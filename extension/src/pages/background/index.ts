import reloadOnUpdate from "virtual:reload-on-update-in-background-script";

reloadOnUpdate("pages/background");

console.log("background loaded");

chrome.runtime.onMessage.addListener(async function (
  request,
  sender,
  sendResponse
) {
  switch (request.type) {
    case "panel": {
      const panelUrl = `chrome-extension://${chrome.runtime.id}/src/pages/panel/index.html`;
      const tabs = await chrome.tabs.query({ url: panelUrl });
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
    case "add": {
      sendResponse({ message: "bookmark added!" });
      break;
    }
  }
});
