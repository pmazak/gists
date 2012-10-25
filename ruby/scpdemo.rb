require 'rubygems'
require 'fileutils'
require 'net/ssh'
require 'net/sftp'
require 'net/ssh/proxy/http'

 
$unixUser = "username" #ARGV[0]
$unixPass = "password" #ARGV[1]
$unixServer = "localhost" #ARGV[2]
proxy_host = 'someproxy.com'
proxy_port = 8080
$proxy = Net::SSH::Proxy::HTTP.new(proxy_host, proxy_port)

def ftp_to_unix()
    puts "[FTP] Connecting to #{$unixServer}"
    Net::SSH.start($unixServer, :port => $port, :proxy => $proxy, 
                   :username => $unixUser, :password => $unixPass) do |session|
        ssh.sftp.connect do |sftp|
            source = "/tmp/a.txt"
            dest = "C:\\tmp\\a.txt"
            puts "[FTP] Copying #{source} to #{dest}"
            sftp.upload!(source, dest)
            puts "[FTP] 1 file copied"
        end
    end
end

ftp_to_unix()