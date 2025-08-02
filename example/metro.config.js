const path = require('path');
const { getDefaultConfig } = require('@react-native/metro-config');

const root = path.resolve(__dirname, '..');

/**
 * Metro configuration for monorepo
 * https://facebook.github.io/metro/docs/configuration
 *
 * @type {import('metro-config').MetroConfig}
 */
const config = getDefaultConfig(__dirname);

module.exports = {
  ...config,
  projectRoot: __dirname,
  watchFolders: [root],
  resolver: {
    ...config.resolver,
    // Include the parent directory so Metro can find the main package
    extraNodeModules: {
      '@rn-nui/alert': root,
    },
    // Resolve source files from the main package
    resolveRequest: (context, moduleName, platform) => {
      if (moduleName === '@rn-nui/alert') {
        return {
          filePath: path.join(root, 'src', 'index.tsx'),
          type: 'sourceFile',
        };
      }

      return context.resolveRequest(context, moduleName, platform);
    },
  },
};
