# Freedom Internet EdgeRouter example

This repositorie contains a sample config.boot configuration file for Ubiquiti EdgeRouter.

This specific (sanitized) file was created for an EdgeRouter X SFP running v2.0.9-hotfix.7 in combination with [Freedom](https://freedom.nl) internet (w. Fiber Crew as the lighting provider). \
It contains the following specifics:
- Internet only
- IPv4 and IPv6
- WAN interface on eth0
- MTU 1500 with MSS clamping
- 1 Gbit/s (hardware offloading enabled)

# Sources
The following sources were used to compose the configuration:
- [David Westerfield - Ubiquiti EdgeRouter 4 IPv6 Setup](https://davidwesterfield.net/2020/06/edgerouter-4-ipv6-setup/)
- [Freedom Community - EdgeOs (Ubiquiti- Edge Router X SFP) default ipv4 config PPPoE over LAN (with NTU)](https://community.freedom.nl/t/edgeos-ubiquiti-edge-router-x-sfp-default-ipv4-config-pppoe-over-lan-with-ntu/1066) (Dutch)
- [KPN Forum - IPv6 werkt niet meer na overgang van xs4all op KPN infra](https://forum.kpn.com/internet-9/ipv6-werkt-niet-meer-na-overgang-van-xs4all-op-kpn-infra-563402) For adding the default static route for IPv6 (Dutch)
