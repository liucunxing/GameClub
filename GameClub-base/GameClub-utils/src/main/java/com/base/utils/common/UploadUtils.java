package com.base.utils.common;
import com.jcraft.jsch.*;

public class UploadUtils {
    public static void uploadFile(String localFilePath, String remoteFilePath) {
        JSch jsch = new JSch();
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            session = (Session) jsch.getSession("root", "47.113.144.107", 22); // 设置远程服务器的用户名、服务器地址和端口
            session.setPassword("Birthday010429"); // 设置远程服务器的密码
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.put(localFilePath, remoteFilePath); // 上传文件

        } catch (JSchException | SftpException e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}

