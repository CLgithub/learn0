#!/bin/bash
case $1 in
"start")
	for line in vUbuntu1 vUbuntu2 vUbuntu3
	do
		echo "--- starting $line ---"
		ssh $line "/home/l/develop/eagle/efak-web-2.1.0/bin/ke.sh start"
	done
;;
"stop")
	for line in vUbuntu1 vUbuntu2 vUbuntu3
	do
		echo "--- stopting $line ---"
		ssh $line "/home/l/develop/eagle/efak-web-2.1.0/bin/ke.sh stop"
	done
;;
esac
