怎样使用Linux下的ip命令

# ip命令
可以通过ip命令管理ip地址，网络接口控制器以及路由规则。ip命令立即生效，不需要重启。
ip命令格式：

```shell
ip [ OPTIONS ] OBJECT { COMMAND | help }
```

ip有很多子对象，OBJECT就代表这些子对象，包括：
{ link | address | addrlabel | route | rule | neigh | ntable | tunnel | tuntap | maddress | mroute | mrule | monitor | xfrm | netns | l2tp |
               tcp_metrics | token | macsec }  
其中常用的有：
- address：IP地址和范围。
- link：网络接口，例如有线连接和Wi-Fi适配器。
- route：管理addresses 通过接口（links）发送的流量路由的规则。

OPTIONS：  

```shell
OPTIONS={ -V[ersion] | -s[tatistics] | -d[etails] | -r[esolve] | -h[uman-readable] | -iec | -f[amily] { inet | inet6 | ipx | dnet | link } | -o[neline] | -t[imestamp] | -b[atch] [filename] | -rc[vbuf] [size] }
```

- V：显示命令的版本信息；
- s：输出更详细的信息；
- f：强制使用指定的协议族；
- 4：指定使用的网络层协议是IPv4协议；
- 6：指定使用的网络层协议是IPv6协议；
- 0：输出信息每条记录输出一行，即使内容较多也不换行显示；
- r：显示主机时，不使用IP地址，而使用主机的域名

## ip address
demo

```shell
#不同的调用方式
ip address
ip addr
ip address show
ip addr show
ip a
#查看帮助信息
ip address help
#仅显示ipv4。
ip -4 addr
#仅显示ipv6。
ip -6 addr
#指定网卡
ip addr show dev lo
#指定ipv4以及网卡
ip -4 addr show dev enp0s3
```

展示的数据信息解析：
- **lo**：网络接口名称，为字符串。  
- **<LOOPBACK，UP，LOWER_UP>**：这是一个回环接口。 UP状态表示可以操作。物理网络层 也是up状态。
- **mtu 65536**：最大传输单位。这是此接口可以传输的最大数据块的大小。
- **qdisc noqueue**： Aqdisc是一种排队机制。它计划数据包的传输。noqueue意味着“立即发送，请勿排队”。这是qdisc虚拟设备的默认规则，例如环回地址。
- **state UNKNOWN**：可能是DOWN（网络接口无法运行），UNKNOWN（网络接口可以运行，但没有任何连接）或 UP（网络可以运行并且存在连接）。
- **group default**：接口可以按逻辑分组。默认值是将它们全部放在称为“default”的组中。
- **qlen 1000**：传输队列的最大长度。
- **link / loopback**：接口的媒体访问控制（MAC）地址。
- **inet 127.0.0.1/8**：IP版本4地址。在正斜杠（/）之后的地址部分是代表子网掩码的无类域间路由表示法（CIDR）。它指示在子网掩码中将多少个前导连续位设置为1。值八表示八位。设置为1的8位二进制表示255，因此子网掩码是255.0.0.0。
- **scope host**： IP地址作用域。此IP地址仅在计算机内部（“host”）有效。
- **lo**：与该IP地址关联的接口。
- **valid_lft**：有效生存期。对于由动态主机配置协议 （DHCP）分配的IP版本4 IP地址，这是该IP地址被视为有效并且能够发出和接受连接请求的时间长度。
- **preferred_lft**：首选寿命。对于DHCP分配的IP版本4 IP地址，这是IP地址可以不受限制地使用的时间。该valid_lft值不得大于该值。
- **INET6**：IP版本6地址scope，valid_lft和preferred_lft。

物理接口的数据信息解析：
- **enp0s3**: 网络接口的名字。en表示ethernet，p0表示网卡的总线编号，s3表示插槽号。
- **<BROADCAST,MULTICAST,UP,LOWER_UP>**: 物理接口支持广播，接口的状态是up（可操作，可链接）。物理层也是up。
- **mtu 1500**: 物理接口支持的最大传输单元。
- **qdisc fq_codel**: 调度程序正在使用一种称为“公平队列，受控延迟”的规则。它旨在为使用队列的所有流量提供公平的带宽份额。
- **state UP**: 接口可操作且已连接。
- **group default**: 此接口在“default”接口组中。
- **qlen 1000**: 传输队列的最大长度。
- **link/ether**: 接口的MAC地址。
- **inet 192.168.4.26/24**: IP版本4地址。“/24”告诉我们在子网掩码中设置了24个连续的前导位。那是八位的三组。八位二进制数等于255；因此，子网掩码是255.255.255.0。
- **brd 192.168.4.255**：此子网的广播地址。
- **scope global**: IP地址在此网络上的所有地方均有效。
- **dynamic:** 接口故障时IP地址丢失。
- **noprefixroute**: 添加此IP地址后，请勿在路由表中创建路由。如果某人想使用该IP地址，则必须手动添加一条路由。同样，如果此IP地址被删除，则不要寻找要删除的路由。
- **enp0s3**: 与该IP地址关联的接口。
- **valid_lft**: 有效生存期。IP地址被视为有效的时间。
- **preferred_lft**: 首选寿命。IP地址运行的时间没有任何限制。
- **inet6**: IP版本6地址scope，valid_lft和preferred_lft。

## ip 地址操作
您可以使用add和dev选项将IP地址添加到接口。您只需要告诉ip命令要添加哪个IP地址以及将其添加到哪个接口即可。  
我们将向enp0s3接口添加IP地址192.168.4.44 。我们还必须提供子网掩码的CIDR表示法。  
我们输入以下内容：
```
sudo ip addr add 192.168.4.44/24 dev enp0s3
```
和添加ip地址类似，可以删除ip地址。

```
sudo ip addr del 192.168.4.44/24 dev enp0s3
```

## 网络接口操作
查看网络接口：

```
ip link show
```
查询指定网络接口：

```
ip link show enp0s3
```
启停网络接口：

```
sudo ip link set enp0s3 down
sudo ip link set enp0s3 up
```

## 管理路由
使用该route对象，您可以检查和操纵路线。路由定义到不同IP地址的网络流量转发到何处以及通过哪个网络接口转发。  
如果目标计算机或设备与发送计算机共享一个网络，则发送计算机可以将数据包直接转发给它。  
但是，如果目标设备未直接连接，则发送方计算机会将数据包转发到默认路由器。然后，路由器决定将数据包发送到何处。  
要查看计算机上定义的路由，请键入以下命令：  

```
ip route
```
打印出的信息：

```
default via 10.47.1.254 dev wlp3s0 proto dhcp metric 600 
169.254.0.0/16 dev wlp3s0 scope link metric 1000 
10.47.0.0/23 dev wlp3s0 proto kernel scope link src 10.47.0.81 metric 600 
172.17.0.0/16 dev docker0 proto kernel scope link src 172.17.0.1 linkdown 
```

路由信息解析：
- **default**： 默认规则。如果没有其他规则与发送的规则匹配，则使用此路由。
- **via 10.47.1.254**：通过设备在192.168.4.1路由数据包。这是该网络上默认路由器的IP地址。
- **dev wlp3s0**：使用此网络接口将数据包发送到路由器。
- **proto  dhcp**：路由协议标识符。DHCP表示将动态确定路由。
- **metric 100**： 与其他路由相比，该路由的优先级的指示。与具有较高度量的路由相比，优先使用具有较低度量的路由。您可以使用它通过Wi-Fi优先使用有线网络接口。

第二条路由将流量控制到IP范围169.254.0.0/16。这是一个零配置网络，这意味着它会尝试针对Intranet通信进行自我配置。但是，您不能使用它在直接网络之外发送数据包。

零配置网络背后的原理是它们不依赖于存在的DHCP和其他活动的服务。他们只需要查看TCP / IP即可自我识别网络上的每个其他设备。

让我们来看看：

- **169.254.0.0/16**：此路由规则控制的IP地址范围。如果计算机在此IP范围内进行通信，则该规则将生效。
- **dev enp0s3**：由该路由控制的流量将使用的网络接口。
- **scope link**：作用域为link，这意味着作用域仅限于本计算机直接连接到的网络。
- **metric 1000** ：这是一个很高的指标，不是首选路线。
 

第三条路由将流量控制到IP地址范围192.168.4.0/24。这是此计算机连接到的本地网络的IP地址范围。它用于在该网络之间但在该网络内部进行通信。

让我们分解一下：
- **10.47.0.0/23**： 此路由规则控制的IP地址范围。如果计算机在此IP范围内通信，则此规则将触发并控制数据包路由。
- ** dev wlp3s0**：此路由将通过其发送数据包的接口。
- **proto kernel**：内核在自动配置期间创建的路由。
- **scope link**： 作用域为link，这意味着作用域仅限于此计算机所连接的直接网络。
- **src 10.47.0.81**：此路由发送的数据包所源自的IP地址。
- **metric 600**：此低度量标准表示首选路由。

查看指定route的信息：

```
ip route list 10.47.1.254/23
```

为新增的网卡添加route：

```
sudo ip route delete default via 192.168.4.1 dev enp0s8
```
删除route：

```
sudo ip route delete 192.168.4.0/24 dev enp0s8
```

## 注意
所有的操作都是临时的。如果重启电脑，所有的操作都会丢失。

## 实操

```SHELL
#打印所有的ipv4网卡以及IP地址
ip -f inet -o address show | grep -v "127.0.0.1" | awk '{if (NR == 1) {print "    网卡：" $2 " ip：" $4 " SELECTED"} if(NR >1 ){print "    网卡：" $2 " ip：" $4}}'

#打印所有的ipv4列表
ip -f inet -o address show | grep -v "127.0.0.1" | awk '{print $4}'| awk 'BEGIN{FS="/"}{print $1}'

# 取第一ip地址作为默认ip
ip -f inet -o address show | grep -v "127.0.0.1" | awk '{if (NR == 1) {print $4}}'| awk 'BEGIN{FS="/"}{print $1}'

```
