package com.frostnerd.dnschanger.config.bean;

import android.net.LinkProperties;
import android.text.TextUtils;

import com.frostnerd.dnschanger.database.entities.IPPortPair;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class DNSProperties {
    private String networkName;
    private List<IPPortPair> ipv4Servers = new ArrayList<>(),
            ipv6Servers = new ArrayList<>();

    public DNSProperties(LinkProperties properties){
        if(TextUtils.isEmpty(properties.getInterfaceName()))networkName = "unknown";
        else networkName = properties.getInterfaceName();

        for(InetAddress address: properties.getDnsServers()){
            if(address == null) continue;
            if(address instanceof Inet6Address) ipv6Servers.add(IPPortPair.wrap(address.getHostAddress(), 53));
            else if(address instanceof Inet4Address)ipv4Servers.add(IPPortPair.wrap(address.getHostAddress(), 53));
        }
    }

    @Override
    public String toString() {
        return networkName;
    }

    private void destroy(){
        ipv4Servers.clear();
        ipv6Servers.clear();
        ipv4Servers = null;
        ipv6Servers = null;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        destroy();
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public List<IPPortPair> getIpv4Servers() {
        return ipv4Servers;
    }

    public void setIpv4Servers(List<IPPortPair> ipv4Servers) {
        this.ipv4Servers = ipv4Servers;
    }

    public List<IPPortPair> getIpv6Servers() {
        return ipv6Servers;
    }

    public void setIpv6Servers(List<IPPortPair> ipv6Servers) {
        this.ipv6Servers = ipv6Servers;
    }
}
