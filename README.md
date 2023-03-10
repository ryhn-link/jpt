![](screenshot.png)
# Just Placeholder Tablist
## Usage
* Compile the plugin
* Put it in your `plugins` directory
* Download [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/), if you haven't already
* Restart/reload server
* Modify `plugins/JustPlaceholderTab/config.yml` to your liking
* Use the `/reloadtablist` to reload the tablist format

## Troubleshooting
### I can't run the /reloadtablist command
Make sure you have the `jpt.command.reloadtablist` permission or are a server operator.
### The tablist shows %something_xyz%
You're missing a PlaceholderAPI expansion, try running `/papi ecloud download something`, where `something` is the first part of the string.