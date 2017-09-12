git-tips
===

##  利用方法
### リモートにて空のリポジトリを作成し、ローカルからPushする方法
#### リモート
　例えば、githubにてリポジトリ(test)を作成
#### ローカル
```
echo "# life" >> README.md
git init
git add README.md
git commit -m "first commit"
git remote add origin git@github.com:gituser/life.git
git push -u origin master
```
behind proxy環境では、.ssh/configにてproxy接続項目を定義

## ssh認証設定


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
