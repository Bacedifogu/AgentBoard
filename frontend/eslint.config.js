import eslint from '@eslint/js';
import tseslintPlugin from '@typescript-eslint/eslint-plugin';
import tseslintParser from '@typescript-eslint/parser';
import angularPlugin from '@angular-eslint/eslint-plugin';

export default [
  {
    ignores: ['**/*.html', 'node_modules/**', 'dist/**', 'out-tsc/**'],
  },
  eslint.configs.recommended,
  {
    files: ['**/*.ts'],
    languageOptions: {
      parser: tseslintParser,
      parserOptions: {
        project: './tsconfig.app.json',
      },
      globals: {
        window: 'readonly',
        document: 'readonly',
        localStorage: 'readonly',
        sessionStorage: 'readonly',
        console: 'readonly',
        confirm: 'readonly',
        alert: 'readonly',
      },
    },
    plugins: {
      '@typescript-eslint': tseslintPlugin,
      '@angular-eslint': angularPlugin,
    },
    rules: {
      ...tseslintPlugin.configs.recommended.rules,
      ...angularPlugin.configs.recommended.rules,
      '@angular-eslint/component-class-suffix': ['error', { suffixes: ['Component', 'Page', 'Modal'] }],
      '@angular-eslint/directive-class-suffix': ['error', { suffixes: ['Directive'] }],
      '@typescript-eslint/no-unused-vars': ['error', { argsIgnorePattern: '^_' }],
      '@typescript-eslint/explicit-function-return-type': 'off',
      '@typescript-eslint/no-explicit-any': 'warn',
      'no-undef': 'error',
    },
  },
];
