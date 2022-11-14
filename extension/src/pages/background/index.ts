import reloadOnUpdate from "virtual:reload-on-update-in-background-script";

reloadOnUpdate("pages/background");

console.log("background loaded");

chrome.runtime.onMessage.addListener(async function (
  request,
  sender,
  sendResponse
) {
  switch (request.type) {
    case "panel":
      await chrome.tabs.create({
        active: true,
        url: `chrome-extension://${chrome.runtime.id}/src/pages/panel/index.html`,
      });
      break;
    case "add":
      sendResponse({ message: "bookmark added!" });
      break;
  }
});
