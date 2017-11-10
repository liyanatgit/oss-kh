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
<VirtualHost *:10443>
    ServerName https://www.example.com:443
    SetEnv HTTPS on
</VirtualHost>

参考：
https://qiita.com/kuwa72/items/f54da8300a075e0a148b
http://hogem.hatenablog.com/entry/20081116/1226840713

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

## ★　Linux運用
### メール関連
Postfix + MySQL (MariaDB)でメール転送の設定をする
https://qiita.com/mzdakr/items/9c139cd27e05da3cdc1b
Postfixをインストールしてメール送信してみる
http://blog.jicoman.info/2013/08/postfix_install/
[linux][sSMTP] 自前で smtp サーバを用意せずにメール送信する
http://bashalog.c-brains.jp/12/07/31-185952.php

### LogWatch
CentOS 7.0 - ログ解析ツール LogWatch 導入！
https://www.mk-mode.com/octopress/2014/09/06/centos-7-0-installation-of-logwatch/
https://www.jdbc.tokyo/2014/10/centos7-logwatch-install/
Linux - Having a daily report of servers by mail
http://ccm.net/faq/789-linux-having-a-daily-report-of-servers-by-mail
