module.exports = {
    root: true,

    env: {
        browser: true,
        es2021: true,
        node: true,
        // NAJWAŻNIEJSZA ZMIANA: Ta linia informuje ESLint o globalnych makrach Vue 3
        'vue/setup-compiler-macros': true
    },

    extends: [
        'eslint:recommended',
        'plugin:vue/vue3-essential'
    ],

    parser: 'vue-eslint-parser',

    parserOptions: {
        parser: '@babel/eslint-parser',
        sourceType: 'module',
        requireConfigFile: false
    },

    rules: {
        'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off'
    }
};