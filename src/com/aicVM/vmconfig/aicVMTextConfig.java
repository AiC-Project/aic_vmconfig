package com.aicVM.vmconfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class aicVMTextConfig {
    private final String GetPropCmd = "/system/bin/aicVM-prop get ";
    private final String SetPropCmd = "/system/bin/aicVM-prop set ";
    private final String RmPropCmd = "/system/bin/aicVM-prop rm ";
    private final String GetIpInfoCmd = "ifconfig eth0";

    private String execNativeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) >0) {
                output.append(buffer, 0, read);
            }
            reader.close();

            process.waitFor();

            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIpInfo() {
        return execNativeCommand(GetIpInfoCmd);
    }

    public String getValue(String name) {
        return execNativeCommand(GetPropCmd+" "+name);
    }

    public void setValue(String key, String name) {
        execNativeCommand(SetPropCmd+" "+key+" "+name);
    }

    public void removeValue(String key) {
        execNativeCommand(RmPropCmd+" "+key);
    }

}
