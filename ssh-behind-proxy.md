ssh behind proxy
===
## 1. Linux
### 1.1 必要なパッケージ
    #yum info openssh-clients
    #yum info connect

### 1.2 鍵生成
 ssh-keygen -t rsa

### 1.3 .ssh/configにて設定
設定例（~/.ssh/config）：

    # Common conf
    ServerAliveInterval 60

    # proxy to github
    Host github-p
        HostName ssh.github.com
        Port 443
        ProxyCommand connect -H $proxy:$port %h %p

* Point:  
 [ProxyCommand connect -H $proxy:$port %h %p]を追記  
* configの設定項目一覧は、man sshから、[-o option]より確認可能  
* 設定項目詳細は、man ssh_configにて確認

### 1.4 接続先サーバにて、公開鍵を追加
追加先: /home/user/.ssh/authorized_keys

### 1.5 接続確認
    ssh -v git@github-p

*　Point:  -v　#接続時の詳細情報を表示（Debug時便利）


## 2. Windows
git-bashを利用し、設定はLinuxの場合とほぼ同じ

## 3.Mac


## 4.Ref
#### 4.1  [Windows7からGitHubへSSH接続する手順(プロキシ環境有)](http://qiita.com/busonx/items/2efc10a18d7a46f14555)

#### 4.2 [~/.ssh/configについて](http://qiita.com/passol78/items/2ad123e39efeb1a5286b)
