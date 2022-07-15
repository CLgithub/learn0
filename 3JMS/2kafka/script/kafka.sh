#!/bin/bash
case $1 in
"start")
	for line in vUbuntu1 vUbuntu2 vUbuntu3
	do
		echo "--- starting $line zk ---"
		ssh $line "/home/l/develop/kafka_2.13-3.2.0/bin/kafka-server-start.sh -daemon /home/l/develop/kafka_2.13-3.2.0/config/server.properties"
	done
;;
"stop")
	for line in vUbuntu1 vUbuntu2 vUbuntu3
	do
		echo "--- stopting $line zk ---"
		ssh $line "/home/l/develop/kafka_2.13-3.2.0/bin/kafka-server-stop.sh"
	done
;;
esac
