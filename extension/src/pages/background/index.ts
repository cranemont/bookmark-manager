import reloadOnUpdate from "virtual:reload-on-update-in-background-script";

reloadOnUpdate("pages/background");

console.log("background loaded");

chrome.runtime.onMessage.addListener((request, sender, sendResponse) => {
  (async () => {
    switch (request.type) {
      case "open-panel": {
        const panelUrl = `chrome-extension://${chrome.runtime.id}/src/pages/panel/index.html`;
        const tabs = await chrome.tabs.query({ url: panelUrl });

        // create or go to panel tab
        if (tabs.length > 0) {
          await chrome.tabs.highlight({ tabs: tabs[0].index });
        } else {
          await chrome.tabs.create({
            active: true,
            url: panelUrl,
          });
        }

        break;
      }
      case "add-bookmark": {
        // add bookmark
        const res = await fetch("http://43.201.119.242/bookmark", {
          method: "POST",
          headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
          },
          body: JSON.stringify(request.data),
        });

        // send response
        if (res.ok) {
          sendResponse({ status: "success", message: "bookmark added!" });
        } else {
          sendResponse({ status: "error", message: "add bookmark failed." });
        }

        break;
      }
      case "get-user": {
        const { user } = await chrome.storage.local.get(["user"]);
        console.log(user);
        if (user) {
          sendResponse({ status: "success", message: null, user });
        } else {
          sendResponse({
            status: "error",
            message: "Please go to the panel and login.",
            user: null,
          });
        }
        break;
      }
      case "set-user": {
        await chrome.storage.local.set({ user: request.data });
        break;
      }
    }
  })();
  return true;
});
