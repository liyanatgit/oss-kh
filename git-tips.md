git-tips
===
Pro Git 日本語版電子書籍公開サイト
http://blog.bonar.jp/entry/2014/09/04/223048
http://progit-ja.github.io/
https://git-scm.com/book/ja/v2

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

## 一般的なGitコマンド
```
##ファイル操作、Stage, Commit
git status
git add *
git commit -m "some comments"

##discard unstaged Change
git checkout -- file-name
##discard staged Change
git reset HEAD fine-name

##サーバとの同期
git pull
git push

##ログ、diff
git log <file-name>

##git logから、commit番号を探し、そのcommit番号と最新版の差分を出力
git diff <commit>..HEAD <file-name>

##git logから、複数commit番号間でのdiff
git diff <commit1>..<commit2> <file-name>

## commitの撤回
git reset --soft HEAD^

--hardオプション：コミット取り消した上でワークディレクトリの内容も書き換えたい場合に使用。
--softオプション：ワークディレクトリの内容はそのままでコミットだけを取り消したい場合に使用。

HEAD^：直前のコミットを意味する。

HEAD~{n} ：n個前のコミットを意味する。

HEAD^やHEAD~{n}の代わりにコミットのハッシュ値を書いても良い。
gitのv1.8.5からは、「HEAD」のエイリアスとして「＠」が用意されている。
HEAD~とHEAD^と@^は同じ意味。
HEAD^^^とHEAD~3とHEAD~~~とHEAD~{3}と@^^^は同じ意味。

##Commitの打ち消し
作業ツリーを指定したコミット時点の状態にまで戻し、コミットを行う（コミットをなかったことにはせず、逆向きのコミットをすることで履歴を残す）には、
git revert コミットのハッシュ値

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

##stage and commit
git status
git commit -a -m "comments"

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


### リモートリポジトリは、LAN内のLinuxサーバにある場合
例えば、git.localというサーバに、/opt/git配下、
gittestのリポジトリをCloneする場合
```
git clone gituser@git.local:/opt/git/gittest gittest
```
/opt/git/gittestには、利用ユーザのRead/Write権限が必要である。

### Gitで古いバージョンを取得
http://www.nekonotechno.com/nekopress/?p=2548

### 複数リポジトリ、中継点となる git リポジトリ関係
http://d.hatena.ne.jp/Naruhodius/20110418/1303111779

git - 複数の遠隔地からプル/プッシュ
http://code.i-harness.com/ja/q/cf59c

```
host-a : internet (git server: bare repository)
host-b:  proxy & work-machine (bare/non-bare repository)
host-c:  local machine (non-bare repository)

1 host-b is the git proxy & working Machine

1.1 clone bare repository for local machines
$ cd ~/git-bare
$ git clone --bare user1@host-a:/opt/git/testrepo testrepo.git

1.2 clone non-bare repository for working
$ cd ~/git
$ git clone user1@host-a:/opt/git/testrepo

1.3 add remote url for local bare
$ cd ~/git/testrepo
$ git remote add localbare ~/git-bare/testrepo.git

1.4 設定済みのすべてのリモートからフェッチし、追跡ブランチを更新し、HEADにマージしない場合は
$ git remote update

1.5 local bare repoからフェッチ
$ git pull localbare master

1.6 git-pullall , git-pushall
$ vi ~/.bashrc
---
git-pullall () { for RMT in $(git remote); do git pull -v $RMT $1; done; }    
alias git-pullall=git-pullall
git-pushall () { for RMT in $(git remote); do git push -v $RMT $1; done; }
alias git-pushall=git-pushall
---
$ git-pullall
$ git-pushall

2 host-c: clone bare repo from host-b
$ git clone user1@host-b:/home/user1/git-bare/testrepo.git

```

### Linuxでssh接続用のgitサーバを立てる
https://git-scm.com/book/ja/v2/
4.4 Gitサーバー - サーバーのセットアップ

```
1. add git user and prepare git repository
$ sudo useradd git
$ sudo su - git
$ mkdir .ssh && chmod 700 .ssh
$ touch .ssh/authorized_keys && chmod 600 .ssh/authorized_keys
必要に応じて、利用者の公開鍵をauthorized_keysに追記
$ vi .ssh/authorized_keys

$ exit
$ sudo mkdir /opt/git && sudo chown git:git /opt/git

2. init a bare git repository
$ sudo su - git
$ cd /opt/git/
$ mkdir testrepo.git && cd testrepo.git
$ git init --bare

3. clone repository from local machine
$ git clone git@gitserver:/opt/git/testrepo.git

```



### Linux版でのGitバージョンアップ
https://qiita.com/sirone/items/2e233ab9697a030f1335
```
$ sudo yum remove git
$ sudo yum install gcc curl-devel expat-devel gettext-devel openssl-devel zlib-devel perl-ExtUtils-MakeMaker

2.14.2  (2017/10/18 newest stable one)
#GITVERSION="2.14.2" && wget https://www.kernel.org/pub/software/scm/git/git-$GITVERSION.tar.gz && tar -zxf git-$GITVERSION.tar.gz && cd git-$GITVERSION && unset GITVERSION && make prefix=/usr/local all && make prefix=/usr/local install && git --version
```


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

## github.ioにてコンテンツの公開

### 1.自前でコンテンツを用意する場合
+ 1.1 create a repository named username.github.io
+ 1.2 clone the repository
git clone https://github.com/username/username.github.io
+ 1.3 Hello world. enter the project folder and add an index.html
cd username.github.io && echo "Hello World" > index.html
+ 1.4 Push it to the world.
git add * && git commit -m "hello world" && git push

### 2.Git project site
+ 1.1 repository settings (you can set source to /docs folder.)
+ 1.2 Theme chooser (github pages -- choose a theme)
+ 1.3 Pick a theme or use your
+ 1.4 Edit Content and commit.
+ 1.5 ...and you're done.
Fire up a browser and go to http://username.github.io/repository.

### Ref
github.io: how to change markdown file to html
http://yoshikyoto.github.io/text/git/gh_pages_md.html
