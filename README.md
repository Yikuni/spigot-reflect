---

A reflect frame that helps to simplize the development of spigot. The feature includes Command, Event Listener, Recipe and Menu. With annotation, it makes code more elegent and let developer focus on the game function.

---

## Quick Start
### 1. add maven dependency in pom.xml
```xml
<!--add in repositories  -->
<repository>
    <id>yikuni-repo</id>
    <url>https://raw.github.com/yikuni/maven-repo/master/</url>
</repository>

<!--add in dependency  -->
<dependency>
    <groupId>com.yikuni.mc</groupId>
    <artifactId>spigot-reflect</artifactId>
    <version>1.1.0</version>
</dependency>
```
### 2. Run PluginLoader in Main Plugin Class
```java
public final class DemoPlugin extends JavaPlugin{
	...

    @Override
    public void onEnable() {
        // Plugin startup logic
        // This code will scan classes and load them
        PluginLoader.run(DemoPlugin.class);
    }
}
```
### 3. Write a simple Event
> its advised to use kotlin for faster development

```kotlin
@YikuniEvent
class PlayerLoginOutEvent: Listener{
    private val vipService = VipService
    @EventHandler
    fun playerLogin(event: PlayerLoginEvent){
        // for example, setup vip
        vipService.initVip(event.player)
    }
}
```
> you can also use java in the same way

```java
@YikuniEvent
public class PlayerLoginOutevent implements Listener{
	@EventHandler
    public void playerLogin(PlayerJoinEvent event){
        // for example, set join message
    	event.setJoinMessage("Hello world!");
        // ...
    }
}
```
## YikuniEvent
> Used in Listener, simply add annotation @YikuniEvent, you dont need to do anything else in DemoPlugin.class

```java
@YikuniEvent
public class PlayerLoginOutevent implements Listener{
	@EventHandler
    public void playerLogin(PlayerJoinEvent event){
        // for example, set join message
    	event.setJoinMessage("Hello world!");
        // ...
    }
}
```
## YikuniCommand
> Used to setup command, its need to add command name in plugin.yml, but its ok to set permission, usage and so on in the annotation.

```java
@YikuniCommand(value = "grand", description = "command related to grand", permission = "op", usage = "/command <GrandName> <PlayerName>")
public class DemoCommand implements CommandExecutor{
    ...
}
```
```yaml
commands:
  grand:
```
## YikuniRecipe
> add annotation YikuniRecipe on the class and recipe methods

```java
@YikuniRecipe
public class TestRecipe {

    @YikuniRecipe("awa")	// the value stands for the key
    public Recipe testB(NamespacedKey key){
        ShapedRecipe recipe = new ShapedRecipe(key, new ItemStack(Material.DIAMOND, 10));
        recipe.shape(
                " A ",
                " A ",
                " B "
        );
        recipe.setIngredient('A', Material.OAK_WOOD);
        recipe.setIngredient('B', Material.STICK);
        return recipe;
    }

    // you can also add more recipe in the same class
}

```
## YikuniMenu
> Sometimes we need to provide GUI rather than let player use plain command, so we choose to use inventory for GUI. It is complex to setup a menu, but spigot-reflect made it easy. Lets see the demo.

```java
import com.yikuni.mc.reflect.annotation.YikuniMenu
import com.yikuni.mc.reflect.common.Menu

// value stands for menu name, size stands for inventory size and it must be greater than 9
@YikuniMenu(value = "hello Menu", size = 9)
public class DemoMenu extends Menu{
	/**
     * let player open menu
     * @param player    target Player
     * @param inventory menu we created
     */
    public void open(Player player, Inventory inventory){
        ItemStack helloWorld = new ItemStack(Material.Diamond);
        inventory.setItem(4, helloWorld);
    }

    /**
    *	when player choosed Menu Item, we send hello world to him
    */
    public void click(InventoryClickEvent event){
    	if(event.getCurrentItem().getType() == Material.Diamond){
        	player.sendMessage("Hello world!);
        }
    }
}
```
The menu has been setup successfully, and you can call Method MenuFacade.open(player, menuName) to let player open the menu.
Like when player performed command /hellomenu
```java
@YikuniCommand("hellomenu")
public class HelloMenuCommand implements CommandExecutor{
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(sender instanceof Player){
        	MenuFacade.open(player, "hello Menu");
        }
        return true;
}
```
