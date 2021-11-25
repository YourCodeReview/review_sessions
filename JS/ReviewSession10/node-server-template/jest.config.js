export default {
  testRegex: "/tests/.*(test|spec)\\.js$",
  setupFilesAfterEnv: ["<rootDir>/tests/setup.js"],
  setupFiles: ["<rootDir>/tests/init.js"],
  coveragePathIgnorePatterns: ["/node_modules/", "/logs/"]
}