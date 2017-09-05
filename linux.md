Study Linux Command
===============================
[Markdownで行こう！](https://gist.github.com/wate/7072365)


## ★ Linux環境

### ■ 環境変数を設定してコマンドを実行
env　変数名=設定する値 コマンド コマンドオプション  
例：英語にてduコマンドのmanを表示

    $env LANG=en man du

## ★　ネットワーク系

### ■ Linuxホスト名
最高の参照資料：[Linux Hostname Configuration](https://jblevins.org/log/hostname)

確認：
    #hostname
    #uname -n
    [root@megrez ~]# hostnamectl
    Static hostname: megrez.liberty.ntt
        Icon name: computer-vm
           Chassis: vm
        Machine ID:fb3ef517cfb04e2ca9b3106fc114ec2b
        Boot ID:28e6c3b7fb7c42d6ad0c850b6f355af2
        Virtualization: vmware
        Operating System: CentOS Linux 7 (Core)
        CPE OS Name: cpe:/o:centos:centos:7
        Kernel: Linux 3.10.0-327.13.1.el7.x86_64
        Architecture: x86-64
設定・変更
    #hostnamectl set-hostname blaze.liberty.ntt
    #less /etc/hostname
HostnameのIPに対応する            



## ★ ファイル処理系

### ■ HDD用量、サイズの大きいフォルダの確認
    [root@abc /]# du -md1  | sort -nr | head -5
    3046    .
    2017    ./usr
    597     ./var
    218     ./opt
    111     ./boot

du -md1 ==> m: size=M　d1: folder-max-depth=1  
sort -nr ==> ソート  
head -5 ==> 先頭５行  
