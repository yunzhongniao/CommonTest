#打印所有的ipv4网卡以及IP地址
ip -f inet -o address show | grep -v "127.0.0.1" | awk '{if (NR == 1) {print "    网卡：" $2 " ip：" $4 " SELECTED"} if(NR >1 ){print "    网卡：" $2 " ip：" $4}}'

#打印所有的ipv4列表
ip -f inet -o address show | grep -v "127.0.0.1" | awk '{print $4}'| awk 'BEGIN{FS="/"}{print $1}'

# 取第一ip地址作为默认ip
ip -f inet -o address show | grep -v "127.0.0.1" | awk '{if (NR == 1) {print $4}}'| awk 'BEGIN{FS="/"}{print $1}'
