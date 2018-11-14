/*
1. Implement three nodes point – to – point network with duplex links between them.
Set the queue size, vary the bandwidth and find the number of packets dropped.
*/

#include "ns3/core-module.h"
#include "ns3/network-module.h"
#include "ns3/internet-module.h"
#include "ns3/point-to-point-module.h"
#include "ns3/applications-module.h"

// Network topology
//
//       10.1.1.0       10.1.2.0
// n0 -------------- n1..........n2
//    point-to-point
//

using namespace ns3;

NS_LOG_COMPONENT_DEFINE("P2P-LAB1");

int main(int argc, char *argv[])
{
    std::string socketType = "ns3::TcpSocketFactory";

    CommandLine cmd;
    cmd.Parse(argc, argv);

    NodeContainer nodes;
    nodes.Create(3);

    InternetStackHelper stack;
    stack.Install(nodes);

    PointToPointHelper p2p1;
    p2p1.SetDeviceAttribute("DataRate", StringValue("5Mbps"));
    p2p1.SetChannelAttribute("Delay", StringValue("1ms"));

    Ipv4AddressHelper address;
    address.SetBase("10.1.1.0", "255.255.255.0");

    NetDeviceContainer devices;
    devices = p2p1.Install(nodes.Get(0), nodes.Get(1));
    Ipv4InterfaceContainer interfaces = address.Assign(devices);

    devices = p2p1.Install(nodes.Get(1), nodes.Get(2));
    address.SetBase("10.1.2.0", "255.255.255.0");
    interfaces = address.Assign(devices);

    Ptr<RateErrorModel> em = CreateObject<RateErrorModel>();

    //Introduce error model to drop packets
    em->SetAttribute("ErrorRate", DoubleValue(0.00002));
    devices.Get(1)->SetAttribute("ReceiveErrorModel", PointerValue(em));

    Ipv4GlobalRoutingHelper::PopulateRoutingTables();

    uint32_t payloadSize = 1448;
    OnOffHelper onoff(socketType, Ipv4Address::GetAny());

    //Generate traffic
    onoff.SetAttribute("OnTime", StringValue("ns3::ConstantRandomVariable[Constant=1]"));

    onoff.SetAttribute("OffTime", StringValue("ns3::ConstantRandomVariable[Constant=0]"));

    onoff.SetAttribute("PacketSize", UintegerValue(payloadSize));

    onoff.SetAttribute("DataRate", StringValue("50Mbps")); //bit/s

    uint16_t port = 7;

    //1. Install receiver (for packetsink) on node 2
    Address localAddress1(InetSocketAddress(Ipv4Address::GetAny(), port));
    PacketSinkHelper packetSinkHelper1(socketType, localAddress1);
    ApplicationContainer sinkApp1 = packetSinkHelper1.Install(nodes.Get(2));
    sinkApp1.Start(Seconds(0.0));
    sinkApp1.Stop(Seconds(10));

    //2. Install sender app on node 0
    ApplicationContainer apps;
    AddressValue remoteAddress(InetSocketAddress(interfaces.GetAddress(1), port));
    onoff.SetAttribute("Remote", remoteAddress);
    apps.Add(onoff.Install(nodes.Get(0)));
    apps.Start(Seconds(1.0));
    apps.Stop(Seconds(10));

    Simulator::Stop(Seconds(10));

    //Generate trace file
    AsciiTraceHelper ascii;
    p2p1.EnableAsciiAll(ascii.CreateFileStream("P2ptracefile.tr"));

    //Run the simulator
    Simulator::Run();

    Simulator::Destroy();

    return 0;
}
