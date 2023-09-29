<div align="center">

<a href="https://www.spigotmc.org/resources/83636/"><img src="https://img.shields.io/badge/Buy%20the%20plugin-E4A11E" alt="Buy"></a>
<a href="https://wiki.advancedgui.app/wiki/api.html"><img src="https://img.shields.io/badge/Official%20wiki-E46A1E" alt="Wiki"></a>
<a href="https://discord.gg/ycDG6rS"><img src="https://img.shields.io/badge/Official%20Discord%20server-E4531E" alt="Discord"></a>
<a href="https://discord.gg/bKjYgSFd8b"><img src="https://img.shields.io/badge/My%20Discord%20server-E43E1E" alt="Version"></a>
  
  <h1>AdvancedGui API Wiki (Unofficial)</h1>
  <p>You are using the <a href="https://www.spigotmc.org/resources/itemframe-touchscreens-advancedgui.83636/">AdvancedGUI</a> <b>API</b> and the <a href="https://wiki.advancedgui.app/wiki/api.html">official wiki<a> is not enough for you?</p>
  <p>Here you can find some ideas and examples how to implement things using the API.</p>
</div>

## TABLE OF CONTENTS
- [Installation](#Installation)
- [Your extension](#Your-layout-extension)

---

# Installation
<!---Gradle-->  
<details>
<summary>Gradle</summary>
<ul>
<li>  
<details>
<!---Groovy--->  
<summary>Groovy</summary>
  <pre lang="groovy">
repositories {
  maven {
    name ="leoko-dev"
    url = "https://repo.leoko.dev/releases"
  }
}
    <br>
dependencies {
  compileOnly "me.leoko.advancedgui:AdvancedGUI:2.2.2"
}</pre>
</details>
</li>
<!---Kotlin--->
<li>   
<details>   
<summary>Kotlin</summary>
  <pre lang="kotlin">
repositories {
    maven("leoko-dev") {
        setUrl("https://repo.leoko.dev/releases")
    }
}
    <br>
dependencies {
    compileOnly("me.leoko.advancedgui:AdvancedGUI:2.2.2")
}</pre>
</details>
</li>  
</details>
</ul>  
<!---Maven--->
<details>
<summary>Maven</summary>
  
```xml
<repository>
    <id>leoko-dev</id>
    <url>https://repo.leoko.dev/releases</url>
</repository>

<dependency>
    <groupId>me.leoko.advancedgui</groupId>
    <artifactId>AdvancedGUI</artifactId>
    <version>2.2.2</version>
    <scope>provided</scope>
</dependency>
```

</details>

Add this to your **plugin.yml** `depend: [AdvancedGUI]`

---

# Your layout extension
