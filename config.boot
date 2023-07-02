firewall {
    all-ping enable
    broadcast-ping disable
    ipv6-name WANv6_IN {
        default-action drop
        description "WAN inbound traffic forwarded to LAN"
        enable-default-log
        rule 10 {
            action accept
            description "Allow established/related sessions"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    ipv6-name WANv6_LOCAL {
        default-action drop
        description "WAN inbound traffic to the router"
        enable-default-log
        rule 10 {
            action accept
            description "Allow established/related sessions"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
        rule 30 {
            action accept
            description "Allow IPv6 icmp"
            protocol ipv6-icmp
        }
        rule 40 {
            action accept
            description "allow dhcpv6"
            destination {
                port 546
            }
            protocol udp
            source {
                port 547
            }
        }
    }
    ipv6-receive-redirects disable
    ipv6-src-route disable
    ip-src-route disable
    log-martians enable
    name WAN_IN {
        default-action drop
        description "WAN to internal"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    name WAN_LOCAL {
        default-action drop
        description "WAN to router"
        rule 10 {
            action accept
            description "Allow established/related"
            state {
                established enable
                related enable
            }
        }
        rule 20 {
            action drop
            description "Drop invalid state"
            state {
                invalid enable
            }
        }
    }
    options {
        mss-clamp {
            interface-type pppoe
            mss 1448
        }
    }
    receive-redirects disable
    send-redirects enable
    source-validation disable
    syn-cookies enable
}
interfaces {
    ethernet eth0 {
        duplex auto
        mtu 1508
        poe {
            output off
        }
        speed auto
        vif 6 {
            description "Internet (PPPoE)"
            mtu 1508
            pppoe 0 {
                default-route auto
                dhcpv6-pd {
                    pd 0 {
                        interface switch0 {
                            host-address ::1
                            prefix-id :1
                            service slaac
                        }
                        prefix-length /48
                    }
                    prefix-only
                    rapid-commit enable
                }
                firewall {
                    in {
                        ipv6-name WANv6_IN
                        name WAN_IN
                    }
                    local {
                        ipv6-name WANv6_LOCAL
                        name WAN_LOCAL
                    }
                }
                ipv6 {
                    dup-addr-detect-transmits 1
                    enable {
                    }
                }
                mtu 1500
                name-server auto
                password 1234
                user-id fake@freedom.nl
            }
        }
    }
    ethernet eth1 {
        description Local
        duplex auto
        poe {
            output off
        }
        speed auto
    }
    ethernet eth2 {
        description Local
        duplex auto
        poe {
            output off
        }
        speed auto
    }
    ethernet eth3 {
        description Local
        duplex auto
        poe {
            output off
        }
        speed auto
    }
    ethernet eth4 {
        description Local
        duplex auto
        poe {
            output off
        }
        speed auto
    }
    ethernet eth5 {
        duplex auto
        speed auto
    }
    loopback lo {
    }
    switch switch0 {
        address 10.0.9.1/24
        description Local
        ipv6 {
            dup-addr-detect-transmits 1
            router-advert {
                cur-hop-limit 64
                link-mtu 0
                managed-flag true
                max-interval 600
                other-config-flag false
                prefix ::/64 {
                    autonomous-flag true
                    on-link-flag true
                    valid-lifetime 2592000
                }
                reachable-time 0
                retrans-timer 0
                send-advert true
            }
        }
        mtu 1500
        switch-port {
            interface eth1 {
            }
            interface eth2 {
            }
            interface eth3 {
            }
            interface eth4 {
            }
            vlan-aware disable
        }
    }
}
service {
    dhcp-server {
        disabled false
        hostfile-update disable
        shared-network-name LAN {
            authoritative enable
            subnet 10.0.9.0/24 {
                default-router 10.0.9.1
                dns-server 10.0.9.1
                lease 86400
                start 10.0.9.38 {
                    stop 10.0.9.243
                }
            }
        }
        static-arp disable
        use-dnsmasq disable
    }
    dns {
        forwarding {
            cache-size 150
            listen-on switch0
        }
    }
    gui {
        http-port 80
        https-port 443
        older-ciphers enable
    }
    nat {
        rule 5010 {
            description "masquerade for WAN"
            outbound-interface pppoe0
            type masquerade
        }
    }
}
system {
    analytics-handler {
        send-analytics-report false
    }
    crash-handler {
        send-crash-report false
    }
    ntp {
        server 0.ubnt.pool.ntp.org {
        }
        server 1.ubnt.pool.ntp.org {
        }
        server 2.ubnt.pool.ntp.org {
        }
        server 3.ubnt.pool.ntp.org {
        }
    }
    offload {
        hwnat enable
        ipsec enable
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
    }
    time-zone UTC
}