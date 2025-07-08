import type { NextConfig } from "next";

export const experimental = {
  turbo: false, // 👈 désactive Turbopack
};

const nextConfig: NextConfig = {
  webpack(config, { isServer }) {
    if (!isServer) {
      config.devtool = 'source-map';
      config.resolve.fallback = {
        fs: false,
        path: false,
        os: false,
        module: false,
        canvas: false,
      };
    }
    return config;
  },
};

export default nextConfig;
