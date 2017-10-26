git-tips
===

## 初期設定
```
username emailの設定
git config --global user.name "Your Name"
git config --global user.email you@example.com
--globalの場合、すべてのローカルgitリポジトリ間で有効です。

設定確認
git config -l

remoteの設定
git remote add origin git@github.com:hogegit/life
git remote -v

proxyあり、なしの環境の場合、複数のremoteを定義
git remote add org-p git@github-p:hogegit/life

.ssh/configにて、github-pのProxy設定を行い、ネットワーク環境状況次第、
適宜remoteを切替る

例: proxy環境の場合
git pull org-p master
git push org-p master

```

##  利用方法
### リモートにて空のリポジトリを作成し、ローカルからPushする方法
#### リモート
　例えば、githubにてリポジトリ(test)を作成
#### ローカル
```
echo "# life" >> README.md
git init
git status
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:gituser/life.git
git remote -v show
git push -u origin master
```
behind proxy環境では、.ssh/configにてproxy接続項目を定義

### ローカルにて、リモートのリポジトリをCloneする方法

```
$ git clone git@github.com:xxx/oss-kh.git oss-kh
Cloning into 'oss-kh'...
Enter passphrase for key '/Users/xxx/.ssh/id_rsa':
remote: Counting objects: 55, done.
remote: Compressing objects: 100% (40/40), done.
remote: Total 55 (delta 25), reused 37 (delta 13), pack-reused 0
Receiving objects: 100% (55/55), 12.63 KiB | 0 bytes/s, done.
Resolving deltas: 100% (25/25), done.
```

ssh認証以外、https接続の利用も可能  
git clone https://github.com/xxx/oss-kh.git oss-kh


## ssh認証設定

githubのSettingにて、公開鍵を登録  

## bitbucket wiki
 bitbucketのwikiは、以下のようにgit clone可能
```
$git clone https://bitbucket.org/xxx/yyy/wiki/ dongwo-wiki
loginid/pwdを導入すれば、clone完了

```

## proxy設定
### windows git shell
/c/Users/xxx/.ssh/config にて、以下を追記
```
# proxy to github
Host github-p
        HostName ssh.github.com
        User git
        Port 443
        IdentityFile "c:\Users\xxx\.ssh\id_rsa"
        ProxyCommand connect.exe -H $proxy-server:$port %h %p
```

## iOS  git2Go App!

## trouble shooting
####  windowsのatomでpull/push失敗
unable to pull. /bin/sh: ... connect.exe: not found  
==>   
環境変数にPath追加（minghw64/binなど)  
win10proの場合：コントロール パネル\システムとセキュリティ\システム システムの詳細設定から、 「環境変数」を選択して、適宜追加


##  ref
### [サルでもわかるGit入門](https://www.backlog.jp/git-guide/)
  分かりやすい解説マニュアル。コンソル、Windows、Mac対応

###  Linuxでのgitサーバ構築
[自分専用の git サーバーを作ろう](https://jp.linux.com/Linux%20Jp/tutorial/429196-tutorial2015050701)  
git bare利用する場合、サーバにてgituserを作成し、各利用者のssh公開鍵をgituserに登録する方式になりそう。
