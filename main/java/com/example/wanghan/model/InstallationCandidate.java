package com.example.wanghan.model;

public class InstallationCandidate
{
    private String packageName;
    private String packageVersion;
    private String description;
    private String key;

    public InstallationCandidate(String packageName, String packageVersion)
    {
        this.packageName = packageName;
        this.packageVersion = packageVersion;
        this.key = this.packageName + "." + this.packageVersion;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getPackageVersion() {
        return packageVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setPackageVersion(String packageVersion) {
        this.packageVersion = packageVersion;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey()
    {
        return this.key;
    }
}
