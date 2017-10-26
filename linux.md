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

### ■ 認証などが必要なURLのダウンロード

ブラウザよりURLのサイトにログインし、ダウンロードファイルのURLおよび必要となるHttpHeader情報(CookieやAuthorizationなど)をブラウザのプラグインなど（saml-tracer, httpheadersなど）から取得

    wget --header "Cookie: bb_session=s4gxb7x8crugz9yjtwcuvcbivjpwbso5" https://bitbucket.org/xxx/yyy/raw/.../zzz.war

    wget --header "Authorization: Basic dGVzdDp0ZXN0" http://test.example.com/test/xxx.jar

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

### ■ scpでディレクトリをコピー
https://qiita.com/pugiemonn/items/3c80522f477bbbfa1302

1. サーバーからディレクトリをコピーする  
scp -r hoge@example.com:/home/hoge/test /tmp/aaa

2. ローカルのディレクトリをサーバーへコピーする  
scp -r /tmp/aaa hoge@example.com:/home/hoge/test/xxx

コピー先のディレクトが存在しない場合、ディレクトリが新規作成しそのディレクトリ配下にファイルがコピーされる。2階層以上のディレクトリが存在しない場合、[No such file or directory]エラーとなる。  
コピー先のディレクトが存在する場合、そのディレクトの配下に、コピー元をディレクト毎コピーされます。

```
例：
コマンド
scp -r /tmp/aaa hoge@example.com:/home/hoge/test/xxx
が実行される場合
1.1 コピー先サーバで、/home/hoge/testが存在し、xxxが存在しない場合、
xxxディレクトリが生成され、その配下コピー元のaaaディレクトリ配下のファイルが
すべてコピーされる。
1.2 コピー先サーバで、/home/hoge/testが存在しない場合、以下のエラーとなる。
scp: /home/hoge/test/xxx: No such file or directory

2. コピー先サーバで、/home/hoge/test/xxxが存在する場合、
xxx配下にaaaのディレクトがコピーされる。

```
