Study Linux Command
===============================
[Markdownで行こう！](https://gist.github.com/wate/7072365)


## ★ Linux環境

### ■ 環境変数を設定してコマンドを実行
env　変数名=設定する値 コマンド コマンドオプション  
例：英語にてduコマンドのmanを表示

    $env LANG=en man du

### ■　bashが起動時に実行するファイル
http://www.itmedia.co.jp/enterprise/articles/0803/10/news012.html
ユーザーがログインに成功した後、bashは「ログインシェル」として一般的に以下のような流れでファイルを順次検索し、ファイルが存在していれば実行し起動します。

1. /etc/profileを実行
2. /etc/profileによって、/etc/profile.dディレクトリ配下のすべてのファイルを実行
3. ログインユーザーのホームディレクトリにある̃/.bash_profileを実行*
4. ̃/.bash_profileによって、̃/.bashrcを実行
5. ̃/.bashrcによって、/etc/bashrcを実行

　例えば、コマンドラインからのbashの起動、suコマンドで別のユーザーになった場合など、ログインシェルとしての起動ではない場合は、5のみが実行されます。

### ■　ssh认证的设定

```
-- modify ssh policy to accept pki only
#vi /etc/ssh/sshd_config
---
# SSH2による接続のみ許可
Protocol 2
# RSA公開鍵による認証
RSAAuthentication yes
PubkeyAuthentication yes
AuthorizedKeysFile .ssh/authorized_keys
# rootでの認証を禁止
PermitRootLogin no
# rhostsでの認証を禁止
RhostsRSAAuthentication no
# パスワードでの認証を禁止（公開鍵による認証のみにする）
PermitEmptyPasswords no
PasswordAuthentication no

-- sshdの再起動
# systemctl restart sshd

-- add user
# useradd testuser
# passwd testuser

-- generate rsa key, add public-key to authorized_keys
$ ssh-keygen -t rsa
$ vi ~/.ssh/


$ chmod 600 ~/.ssh/authorized_keys

-- remote ssh login to server using pki to confirm.
 1. can't remote login by root
 2. can't remote login by using password
 3. can remote login by public-key
   $ ssh testuser@linux-server

####DEBUG#####
1. Clinet site
 #-vは三つまで利用可能
 $ ssh -vvv hostname

2. Server site
 # sshdのDEBUG起動
  /usr/sbin/sshd -d

 [One Point]
  鍵関連ファイルのpermission設定は十分ご注意
    .ssh 700
  配下のファイルは　600
 .sshのpermissionは700ではない場合、sshdサーバ側のDEBUGモードでは
  以下が出力される：
   Authentication refused: bad ownership or modes for directory /home/testuser/.ssh

-- add user to using sudo
--- using visudo

--- or edit /etc/group to add user to wheel group.
** make sure pam_wheel.so is enabled by /etc/pam.d/su
# usermod -aG wheel testuser
or
# vi /etc/group
---
wheel:x:10:centos,testuser


```

###　■　IPV6無効化


### ■　自動化Shell
Linuxの対話がめんどくさい?そんな時こそ自動化だ！-expect編-  
https://qiita.com/ine1127/items/cd6bc91174635016db9b


### ■　SSLアクセレーター後のApache VirtualHost設定
SSLアクセレーターや前段にSSL集約する場合、Apache VirtualHostによるhttp/https分別する対策

一般的なシンプルでの解決方法は、
Apache側でのVirtualHostにて、SetEnv HTTPS onとなります。

例えば、Apache側のVirtualHostは、HTTPとして80と10443のポートを起動。
HAProxy側での振り分けとして
・80を受けるなら、Apacheの80へ
・443を受けるなら、Apacheの10443（httpとして）へ

ApacheのVirtualHostの設定例（httpでhttpsを分別する）
```
<VirtualHost *:10443>
    ServerName https://www.example.com:443
    SetEnv HTTPS on
</VirtualHost>
```

参考：
https://qiita.com/kuwa72/items/f54da8300a075e0a148b
http://hogem.hatenablog.com/entry/20081116/1226840713


### java and alternatives

#### yumでjdkをインストール
- yumでopenjdkをインストール
```
#yum search openjdk
 利用可能なopenjdkの一覧を取得
#yum install java-1.8.0-openjdk.x86_64
```
- yumでoracle jdkをインストール
```
#cd /tmp
#wget --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u91-b14/jdk-8u91-linux-x64.rpm
# yum localinstall jdk-8u91-linux-x64.rpm
```

firefoxのsaml-tracerにて、oracleサイトにてrpmをダウンロードし、Networkのtracer情報から、上記wgetコマンドを生成する
2016/9/9時点での最新版をダウロンドする場合、以下のコマンドとなります。
```
#wget --header "Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2Ftechnetwork%2Fjava%2Fjavase%2Fdownloads%2Fjdk8-downloads-2133151.html; oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u101-b13/jdk-8u101-linux-x64.rpm

oracle javaの古いバージョンは、下記からダウロンド可能
http://www.oracle.com/technetwork/java/javase/downloads/java-archive-downloads-javase7-521261.html?ssSourceSiteId=otnjp
※oracle idでのログインが必要

--java versionの確認
#java -version
openjdk version "1.8.0_91"
OpenJDK Runtime Environment (build 1.8.0_91-b14)
OpenJDK 64-Bit Server VM (build 25.91-b14, mixed mode)
```

#### alternatives にてJavaVMの切替

最初インストールされたopenjdkとなっていたため、alternativesでJavaVMの切替え
```
#alternatives --config java

2 プログラムがあり 'java' を提供します。

  選択       コマンド
-----------------------------------------------
*+ 1           /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.91-0.b14.el7_2.x86_64/jre/bin/java
   2           /usr/java/jdk1.8.0_91/jre/bin/java

Enter を押して現在の選択 [+] を保持するか、選択番号を入力します: 2

# java -version
java version "1.8.0_91"
Java(TM) SE Runtime Environment (build 1.8.0_91-b14)
Java HotSpot(TM) 64-Bit Server VM (build 25.91-b14, mixed mode)

rpmでローカルのパッケージをインストールする場合、依存関係も含めてyum localinstallを利用するべし。
#yum localinstall jre-7u80-linux-x64.rpm
```

####  手動でalternativesにjavaを登録する方法
- oracle jdk1.7をyum localinstallを行っていでも、alternativesに登録されないようで、
手動で登録可能  

使用法: alternatives --install <リンク> <名前> <パス> <優先度>
```
#alternatives --install /usr/bin/java java /usr/java/jdk1.7.0_80/jre/bin/java 10000

左端の記号の意味は、次のとおり。
「+」
現在選択されているプログラムの行に表示される。
「*」
プログラムが複数登録されているとき、最もpriorityの高いものの行に表示される。
```

#### java 10のインストールとalternatives設定
java：java10（18.3）
JDK 10 Early-Access Builds
http://jdk.java.net/10/
```

#alternatives --install /usr/bin/java java /usr/local/jre-10/bin/java 300

```


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

### ■　Traffic Monitor Tools
Linuxでネットワークの監視を行えるモニタリングコマンド20選
https://orebibou.com/2014/09/linux%E3%81%A7%E3%83%8D%E3%83%83%E3%83%88%E3%83%AF%E3%83%BC%E3%82%AF%E3%81%AE%E7%9B%A3%E8%A6%96%E3%82%92%E8%A1%8C%E3%81%88%E3%82%8B%E3%83%A2%E3%83%8B%E3%82%BF%E3%83%AA%E3%83%B3%E3%82%B0%E3%82%B3/

### ■　Proxy関連
社内Proxyに阻まれていろいろ捗らない人のためのTips
https://qiita.com/sachioksg/items/289e40d69382b1d09811


### ■ Port Forward
http://www.kagami.org/ssh/forwarding.html
https://qiita.com/mechamogera/items/b1bb9130273deb9426f5

■　ローカルフォワード
  (Service Listen Port:23389)--LocalMachine(local ssh port: aabbcc) ---(ssh listen port:22)RemoteServer(Service local port:xxyyzz)  -- (Service Listen Port: 3389)TargetServer

上記の場合、
 ssh -L 23388:TargetServer:3388 RemoteServer
外部からも利用したい場合、-gを付ける。


Port Forward利用時、RemoteServer（Linux）にて以下で確認可能
```
[liyan@hndaws ~]$ netstat -aunt | grep -e 3389 -e 22
tcp   0      0 0.0.0.0:22         0.0.0.0:*             LISTEN      --sshd listen port
tcp   0    748 10.0.0.120:22      122.25.188.118:57427  ESTABLISHED --local:remote ssh conn
tcp   0      0 10.0.0.120:41730   10.0.0.101:3389       ESTABLISHED --remote:target service conn
```

上記の場合のPort状態は、以下となります。
  (Service Listen Port:23389)--LocalMachine(local ssh port: 57427) ---(ssh listen port:22)RemoteServer(Service local port:41730)  -- (Service Listen Port: 3389)TargetServer

■　リモートフォワード
https://qiita.com/Daisuke-Otaka/items/45828bcbcf871a67debe
 ssh -R 8081:TargetServer:80 RemoteServer

https://gist.github.com/scy/6781836
Opening and closing an SSH tunnel in a shell script the smart way

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


## ★　重要なコマンド
### viで高速移動
https://qiita.com/takeharu/items/9d1c3577f8868f7b07b5
```
行移動
0	行の先頭へ(インデント無視して先頭へ)
^	行の先頭へ
$	行の末尾へ
+	下の行の先頭へ
-	上の行の先頭へ

ファイル先頭、末尾
gg ファイル先頭
G　ファイル末尾
```

### hashコマンドって何
https://qiita.com/ottyajp/items/998e4fcf6c131a6e3c1a

### findとxargsであるフォルダ配下ファイルの文字列検索
https://www.xmisao.com/2013/09/01/how-to-use-find-and-xargs.html
```
カレントフォルダ配下のすべてのxmlファイルに対して、blaze文字列を検索する
#find . -name *.xml | xargs grep blaze

xargsは標準入力から一覧を受け取って、それを引数に任意のコマンドを実行するコマンド。良くfindとセットで使われる
```

## ★　システム構築
### メールサーバ構築
EC2にメールサーバを構築(複数ドメイン)
https://qiita.com/shigejun/items/12fff88af10ce41102a3

## ★　Linux運用
### メール関連
Postfix + MySQL (MariaDB)でメール転送の設定をする
https://qiita.com/mzdakr/items/9c139cd27e05da3cdc1b
Postfixをインストールしてメール送信してみる
http://blog.jicoman.info/2013/08/postfix_install/
[linux][sSMTP] 自前で smtp サーバを用意せずにメール送信する
http://bashalog.c-brains.jp/12/07/31-185952.php  
【CentOS】迷惑メール(スパム)扱いされない為の最低限設定しておきたい3つの設定【Postfix】  
http://yuzurus.hatenablog.jp/entry/spam-mail

#### mailx コマンドで、外部のSMTPサーバを経由してメール送信
https://ttandai.info/archives/1913
gmail利用する場合
http://colibri.sblo.jp/article/103423197.html

mailxの設定ファイル：.mailrc
---
account tb {
set smtp-use-starttls
set smtp=smtp://smtp.gmail.com:587
set smtp-auth-user=trustbind2015@gmail.com
set smtp-auth-password=xxxxxx
set from=trustbind2015@gmail.com
set nss-config-dir=certs trustbind2015@gmail.com
set ssl-verify=ignore
 }

 証明書関連：
 ```
 # Create a certificate directory
~]$ mkdir certs

# Create a new database in the certs dir
~]$ certutil -N -d certs

# Need now a chain certificate - May 18, 2015
~]$ wget https://www.geotrust.com/resources/root_certificates/certificates/GeoTrust_Global_CA.cer

# Need now a chain certificate part 2 - May 18, 2015
~]$ mv GeoTrust_Global_CA.cer certs/

# Fetch the certificate from Gmail, saving in the text file GMAILCERT
# Added the CA opion - May 18, 2015
~]$ echo -n | openssl s_client -connect smtp.gmail.com:465 -CAfile certs/GeoTrust_Global_CA.cer | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > GMAILCERT

# Import the new cert file into the new database in the new dir
~]$ certutil -A -n "Google Internet Authority" -t "C,," -d certs -i GMAILCERT

# Double Check
~]$ certutil -L -d certs

Certificate Nickname                                         Trust Attributes
                                                             SSL,S/MIME,JAR/XPI

Google Internet Authority                                    C,,  
```

mailxコマンド
echo "test mail." | mail -v -s "subject" -A tb useraaa@example.com

※ Error in certificate: Peer's certificate issuer is not recognized. が残るが、送信的に問題なし

Gmail側の設定
下記「安全性の低いアプリの許可」を有効にする必要があるかも。
https://myaccount.google.com/lesssecureapps
https://myaccount.google.com/device-activity


linux dailyreportを送信

/root/script/dailyreport.sh
```
#!/bin/bash
MAILBODY=/root/script/mail.txt
echo Daily reporting of `date +%e\ %B\ %Y` for `hostname` > $MAILBODY
echo "   " >> $MAILBODY

#disk
df -h >> $MAILBODY
echo "   " >> $MAILBODY

#system status
w >> $MAILBODY
echo "   " >> $MAILBODY

#secure
echo "Yesterday Invalid User login"  >> $MAILBODY
less /var/log/secure | grep "`date -d '1 days ago ' +'%B %d'`" | grep "invalid user" | wc -l >>  $MAILBODY


echo "Yesterday Session Opened User"  >> $MAILBODY
less /var/log/secure | grep "`date -d '1 days ago ' +'%B %d'`" | grep "session opened"  >>  $MAILBODY

cd ~
mail -s "Daily reporting of `date +%e\ %B\ %Y`" -A tb li_y@weshare.jp < $MAILBODY
```

cronに登録
```
#vi /etc/crontab
---
5 0 * * * root /home/centos/dailyreport.sh
```
※毎日0時5分、rootユーザにてdailyreport.shを実行し、Linuxサーバ状況をメール送信

### LogWatch
CentOS 7.0 - ログ解析ツール LogWatch 導入！
https://www.mk-mode.com/octopress/2014/09/06/centos-7-0-installation-of-logwatch/
https://www.jdbc.tokyo/2014/10/centos7-logwatch-install/
Linux - Having a daily report of servers by mail
http://ccm.net/faq/789-linux-having-a-daily-report-of-servers-by-mail

### 侵入確認
如何判断 Linux 服务器是否被入侵？  
https://linux.cn/article-9116-1.html

### SecTools.Org: Top 125 Network Security Tools
http://sectools.org/

#### wireshark/tshark on centos
http://oxynotes.com/?p=7969
https://blog.s-tajima.work/post/2017/intro-tshark/
```
NIC一覧
#tshark -D

Tranfic to file.
#tshark -i 1 -w traficlog

Parse tranfic detail from file
#tshark -r traficlog -Y http -V
#tshark -r traficlog -Y ssl -V

#tshark -r traficlog -Y 'ssl || http' -V | less
```
https://reberhardt.com/blog/2016/10/10/capturing-https-traffic-with-tshark.html

### cron

```
#vi /etc/crontab
---
30 8 * * * centos /home/centos/dailyreport.sh
```

※毎日8時30分、centosユーザにてdailyreport.shを実行

ログ： /var/log/cron
