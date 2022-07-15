#!/bin/bash
case $1 in
"start")
	for line in vUbuntu1 vUbuntu2 vUbuntu3
	do
		echo "--- starting $line zk ---"
		ssh $line "/home/l/develop/apache-zookeeper-3.8.0-bin/bin/zkServer.sh start"
	done
;;
"stop")
	for line in vUbuntu1 vUbuntu2 vUbuntu3
	do
		echo "--- stopting $line zk ---"
		ssh $line "/home/l/develop/apache-zookeeper-3.8.0-bin/bin/zkServer.sh stop"
	done
;;
"status")
	for line in vUbuntu1 vUbuntu2 vUbuntu3
	do
		echo "--- status $line zk ---"
		ssh $line "/home/l/develop/apache-zookeeper-3.8.0-bin/bin/zkServer.sh status"
	done
;;
esac
