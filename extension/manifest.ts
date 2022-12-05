import packageJson from "./package.json";

const manifest: chrome.runtime.ManifestV3 = {
  manifest_version: 3,
  name: packageJson.name,
  version: packageJson.version,
  description: packageJson.description,
  options_page: "src/pages/options/index.html",
  background: { service_worker: "src/pages/background/index.js" },
  action: {
    default_popup: "src/pages/popup/index.html",
    default_icon: "icon-34.png",
  },
  icons: {
    "128": "icon-128.png",
  },
  web_accessible_resources: [
    {
      resources: ["assets/js/*.js", "icon-128.png", "icon-34.png"],
      matches: ["*://*/*"],
    },
  ],
  permissions: ["bookmarks", "tabs", "cookies"],
  host_permissions: ["https://nother.ml/"],
};

export default manifest;
