

# Spigot Reflect

Edit by Yikuni\<2058187089@qq.com\>

---

A reflect frame that helps to simplize the development of spigot. The feature includes Command, Event Listener, Recipe and Menu. With annotation, it makes code more elegent and let developer focus on the game function.

---

## Quick Start
### 1. add maven dependency in pom.xml
```xml
<!--add in dependency  -->
<dependency>
    <groupId>com.yikuni</groupId>
    <artifactId>spigot-reflect</artifactId>
    <version>1.2.0</version>
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
### 3. Write a simple Event Handler
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
> Used in Listener, simply add annotation @YikuniEvent

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
> Sometimes we need to provide GUI rather than let player use plain command, so we choose to use inventory for GUI. It is complex to setup a menu, but spigot-reflect made it easy.

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
     * @param args some arg here
     */
    public void open(Player player, Inventory inventory, Object... args){
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
The menu has been setup successfully, then you can call Method <mark>MenuFacade.open(player, menuName, args...)</mark> to let player open the menu.
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

## Command Interceptor

sometimes you need to ban certain command, like op shouldn't be able to ban owner, then what you need is an interceptor

```java
@CommandInterceptor(value = "ban", priority = 1)
public class BanInterceptor implements Interceptor{
    // return true if the command should be blocked
    @Override
    public boolean onCommand(Player player, String[] args){
        return player.isOp() && args[0].equals("yikuni")
    }
}
```

## Banner

spigot-reflect support printing a banner on plugin enable.

It's deadly easy to add a banner. Just create banner.txt under resource dir and copy your banner into it.

![image-20221207152521095](https://www.yikuni.com/image/docsImage/banner1.png)

banner.txt

``` txt
   ___                      _               _      _  _          
  | _ \    ___    _ __     (_)      _ _    (_)    | || |  __ _   
  |   /   / -_)  | '  \    | |     | '_|   | |     \_, | / _` |  
  |_|_\   \___|  |_|_|_|  _|_|_   _|_|_   _|_|_   _|__/  \__,_|  
_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|_| """"|_|"""""| 
"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-' 

```

the result go like this:

![image-20221207152521095](https://www.yikuni.com/image/docsImage/banner2.png)

## Settings

you are able to disable log of spigot-reflect and banner option.

The first param is banner, second is log, third is debug mode.

``` java
PluginLoader.run(RemiriyaRaffle.class, new PluginLoader.Settings(false, false, false));
```

