在pom.xml中添加如下

```xml

    <profiles>
        <profile>
            <!-- 测试环境 -->
            <id>dev</id>
            <properties>
                <profiles.active>dev</profiles.active>
            </properties>
        </profile>
        <profile>
            <!-- 生产环境 -->
            <id>prod</id>
            <properties>
                <profiles.active>prod</profiles.active>
            </properties>
        </profile>
    </profiles>
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <!--开启过滤src/main/resources目录下的文件内容，将文件内容中带@@的变量用maven properties指定的参数值替换-->
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```