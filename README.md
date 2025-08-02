# @nui/alert

Native alerts for React Native with enhanced functionality and cross-platform support.

## Installation

```bash
npm install @nui/alert
```

or

```bash
yarn add @nui/alert
```

### iOS Setup

No additional setup required for iOS.

### Android Setup

You'll need to override the theme of your app under `/android/app/src/main/res/values/styles.xml` to something that inherits from Material3 or Material3Expressive.

```xml
<resources>
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.Material3.DayNight.NoActionBar">
      <!-- Customize your theme here -->
    </style>
</resources>
```

### Android Colors Customization

You'll need to follow the M3 guidance around [dialogs](https://m3.material.io/components/dialogs/specs) and [inputs](https://m3.material.io/components/text-fields/specs). When you locate the color token you'd like to change, override it in your `colors.xml` or `styles.xml` file.

### Expo Setup

There is no plugin for Expo yet. You'll need to manually edit the native files as described above.

### Icons for Android Alerts

You'll need to add the icons you want to use under `/android/app/src/main/res/drawable` and reference them by the file name in your alert options. For example, if you add the file `base_cookie_24.xml`, you can use it like this:

```typescript
Alert.alert('Title', 'Message', buttons, { icon: 'base_cookie_24' });
```

> Note: If you use vectors directly from Android Studio, update the tint color like this: `android:tint="?attr/colorSecondary"`. This will follow the M3 specs and ensure the icon color matches the night or day theme.

## Usage

```typescript
import Alert from '@nui/alert';

// Basic alert
Alert.alert('Title', 'Message');
```

## API Reference

### Alert.alert()

Display a basic alert dialog with customizable buttons and options.

#### Parameters

| Parameter | Type                 | Required | Description                 |
| --------- | -------------------- | -------- | --------------------------- |
| `title`   | `string`             | ✅       | The alert title             |
| `message` | `string`             | ❌       | The alert message           |
| `buttons` | `Array<AlertButton>` | ❌       | Array of buttons to display |
| `options` | `AlertOptions`       | ❌       | Additional alert options    |

#### AlertButton Properties

| Property   | Type                                     | Platform | Description                                                                                             |
| ---------- | ---------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------- |
| `text`     | `string`                                 | Both     | Text displayed on the button                                                                            |
| `style`    | `'default' \| 'cancel' \| 'destructive'` | iOS      | Visual style - `destructive` shows red text, `cancel` shows as a bold button, `default` is normal style |
| `position` | `'neutral' \| 'negative' \| 'positive'`  | Android  | Position to place the button at the bottom of the alert                                                 |
| `onPress`  | `() => void`                             | Both     | Function called when button is tapped                                                                   |

#### AlertOptions Properties

| Property             | Type                                 | Platform | Description                                                                        |
| -------------------- | ------------------------------------ | -------- | ---------------------------------------------------------------------------------- |
| `cancelable`         | `boolean`                            | Android  | When `true`, tapping outside the alert dismisses it                                |
| `userInterfaceStyle` | `'unspecified' \| 'light' \| 'dark'` | iOS      | Forces light or dark mode for the alert (respects system setting if `unspecified`) |
| `headerAlignment`    | `'left' \| 'center'`                 | Android  | Horizontal alignment of the title text                                             |
| `icon`               | `string`                             | Android  | Name of the icon file under Android `drawable` folder (without extension)          |

#### Examples

```typescript
// Basic alert
Alert.alert('Success', 'Operation completed successfully');

// Alert with buttons
Alert.alert('Confirm Action', 'Are you sure you want to continue?', [
  { text: 'Cancel', style: 'cancel' },
  { text: 'OK', onPress: () => console.log('OK pressed') },
]);

// Android-specific options
Alert.alert(
  'Custom Alert',
  'This alert has custom styling',
  [
    { text: 'Neutral', position: 'neutral' },
    { text: 'Cancel', position: 'negative' },
    { text: 'OK', position: 'positive' },
  ],
  {
    headerAlignment: 'center',
    icon: 'ic_info',
  }
);
```

#### Video Examples

| iOS                                                         | Android                                                         |
| ----------------------------------------------------------- | --------------------------------------------------------------- |
| https://github.com/user-attachments/assets/13d06f0c-1af2-48d5-8b88-347d1807dff0 | https://github.com/user-attachments/assets/b7b46310-aad4-481f-bead-f43939173ad9|

---

### Alert.prompt()

Display a prompt dialog with text input capabilities.

#### Parameters

| Parameter      | Type                  | Required | Description                         |
| -------------- | --------------------- | -------- | ----------------------------------- |
| `title`        | `string`              | ✅       | The prompt title                    |
| `message`      | `string`              | ❌       | The prompt message                  |
| `buttons`      | `Array<PromptButton>` | ❌       | Array of buttons to display         |
| `type`         | `PromptType`          | ❌       | Input type (default: 'plain-text')  |
| `defaultValue` | `string`              | ❌       | Default input value (iOS only)      |
| `keyboardType` | `string`              | ❌       | Keyboard type to display (iOS only) |
| `options`      | `PromptOptions`       | ❌       | Additional prompt options           |

#### PromptButton Properties

| Property      | Type                                     | Platform | Description                                                                                             |
| ------------- | ---------------------------------------- | -------- | ------------------------------------------------------------------------------------------------------- |
| `text`        | `string`                                 | Both     | Text displayed on the button                                                                            |
| `style`       | `'default' \| 'cancel' \| 'destructive'` | Both     | Visual style - `destructive` shows red text, `cancel` shows as cancel button, `default` is normal style |
| `position`    | `'neutral' \| 'negative' \| 'positive'`  | Android  | Position to place the button at the bottom of the alert                                                 |
| `placeholder` | `string`                                 | Android  | Hint text shown in empty input fields                                                                   |
| `onPress`     | `(value: ReturnValue) => void`           | Both     | Function called when button is tapped, receives the entered text/credentials                            |

#### PromptType Options

- `'plain-text'` - Single text input
- `'secure-text'` - Password input
- `'login-password'` - Login and password inputs

#### ReturnValue

- `'plain-text'` - `string`
- `'secure-text'` - `string`
- `'login-password'` - `{ 'login': string, 'password': string }`

#### PromptOptions Properties

| Property             | Type                                 | Platform | Description                                                                                |
| -------------------- | ------------------------------------ | -------- | ------------------------------------------------------------------------------------------ |
| `cancelable`         | `boolean`                            | Android  | When `true`, tapping outside the prompt dismisses it                                       |
| `userInterfaceStyle` | `'unspecified' \| 'light' \| 'dark'` | iOS      | Forces light or dark mode for the prompt (respects system setting if `unspecified`)        |
| `headerAlignment`    | `'left' \| 'center'`                 | Android  | Horizontal alignment of the title text                                                     |
| `onDismiss`          | `(value: ReturnValue) => void`       | Android  | Called when prompt is dismissed without pressing a button (e.g., back button, outside tap) |
| `placeholder`        | `string`                             | Android  | Hint text shown in empty input fields                                                      |

#### Examples

```typescript
// Basic prompt
Alert.prompt('Enter Name', 'Please enter your name:', [
  { text: 'Cancel', style: 'cancel', position: 'negative' },
  {
    text: 'OK',
    position: 'positive',
    onPress: (value) => console.log('Entered:', value),
  },
]);

// Secure text prompt
Alert.prompt(
  'Enter Password',
  'Please enter your password:',
  [
    { text: 'Cancel', style: 'cancel', position: 'negative' },
    {
      text: 'Login',
      position: 'positive',
      onPress: (value) => console.log('Password:', value),
    },
  ],
  'secure-text'
);

// Login-password prompt
Alert.prompt(
  'Login',
  'Please enter your credentials:',
  [
    { text: 'Cancel', style: 'cancel', position: 'negative' },
    {
      text: 'Login',
      position: 'positive',
      onPress: (value) => {
        if (typeof value === 'object') {
          console.log('Login:', value.login, 'Password:', value.password);
        }
      },
    },
  ],
  'login-password'
);
```

#### Video Examples

| iOS                                                          | Android                                                          |
| ------------------------------------------------------------ | ---------------------------------------------------------------- |
|https://github.com/user-attachments/assets/4ddb9bd3-3ffb-434a-9836-4f297c144270| https://github.com/user-attachments/assets/38c050d4-be1c-4bc0-b4ff-5ab87c328802|

---

### Alert.items()

Display a list of selectable items.

#### Parameters

| Parameter | Type                | Required | Description               |
| --------- | ------------------- | -------- | ------------------------- |
| `title`   | `string`            | ❌       | The items dialog title    |
| `items`   | `Array<ItemButton>` | ❌       | Array of items to display |
| `options` | `ItemOptions`       | ❌       | Additional items options  |

#### ItemButton Properties

| Property  | Type                                     | Platform | Description                                                             |
| --------- | ---------------------------------------- | -------- | ----------------------------------------------------------------------- |
| `text`    | `string`                                 | Both     | Text displayed for the item                                             |
| `style`   | `'default' \| 'cancel' \| 'destructive'` | iOS      | Visual style - `destructive` shows red text, others show normal styling |
| `onPress` | `() => void`                             | Both     | Function called when item is selected                                   |

#### ItemOptions Properties

| Property             | Type                                 | Platform | Description                                                                               |
| -------------------- | ------------------------------------ | -------- | ----------------------------------------------------------------------------------------- |
| `cancelable`         | `boolean`                            | Both     | Whether tapping outside dismisses the dialog (default: `false` on Android, `true` on iOS) |
| `userInterfaceStyle` | `'unspecified' \| 'light' \| 'dark'` | iOS      | Forces light or dark mode for the dialog (respects system setting if `unspecified`)       |
| `headerAlignment`    | `'left' \| 'center'`                 | Android  | Horizontal alignment of the title text                                                    |
| `message`            | `string`                             | iOS      | Subtitle text shown below the title                                                       |
| `cancelButtonText`   | `string`                             | Both     | Text for the cancel/dismiss button                                                        |
| `presentation`       | `'alert' \| 'sheet'`                 | iOS      | Display as centered alert or bottom sheet                                                 |

#### Examples

```typescript
// Basic items list
Alert.items('Choose Option', [
  { text: 'Option 1', onPress: () => console.log('Option 1') },
  { text: 'Option 2', onPress: () => console.log('Option 2') },
  { text: 'Option 3', onPress: () => console.log('Option 3') },
]);

// Items with custom options
Alert.items(
  'Actions',
  [
    { text: 'Edit', onPress: () => console.log('Edit') },
    { text: 'Share', onPress: () => console.log('Share') },
    {
      text: 'Delete',
      style: 'destructive',
      onPress: () => console.log('Delete'),
    },
  ],
  {
    message: 'Choose an action to perform',
    cancelButtonText: 'Close',
    presentation: 'sheet', // iOS only
  }
);
```

#### Video Examples

| iOS                                                         | Android                                                         |
| ----------------------------------------------------------- | --------------------------------------------------------------- |
|https://github.com/user-attachments/assets/ae929049-7d8b-4c10-86fe-60ff854d7d8f| https://github.com/user-attachments/assets/015f9f21-537f-40e8-9be7-a51dd76da70b|

---

### Alert.singleChoice()

#### Android ONLY ‼️

Display a single-choice selection dialog with radio buttons.

#### Parameters

| Parameter | Type                        | Required | Description             |
| --------- | --------------------------- | -------- | ----------------------- |
| `title`   | `string`                    | ✅       | The dialog title        |
| `buttons` | `Array<SingleChoiceButton>` | ✅       | Array of choice options |
| `options` | `SingleChoiceOptions`       | ❌       | Additional options      |

#### SingleChoiceButton Properties

| Property          | Type      | Platform | Description                                                  |
| ----------------- | --------- | -------- | ------------------------------------------------------------ |
| `text`            | `string`  | Android  | Label text shown next to the radio button                    |
| `value`           | `string`  | Android  | Unique identifier returned when this option is selected      |
| `defaultSelected` | `boolean` | Android  | Whether this option should be pre-selected when dialog opens |

#### SingleChoiceOptions Properties

| Property          | Type                                                           | Platform | Description                                                              |
| ----------------- | -------------------------------------------------------------- | -------- | ------------------------------------------------------------------------ |
| `cancelable`      | `boolean`                                                      | Android  | When `true`, tapping outside the dialog dismisses it                     |
| `headerAlignment` | `'left' \| 'center'`                                           | Android  | Horizontal alignment of the title text                                   |
| `positive`        | `{ text: string; onPress?: (selectedValue?: string) => void }` | Android  | Button to show in the positive position in the alert (right most button) |
| `negative`        | `{ text: string; onPress?: (selectedValue?: string) => void }` | Android  | Button to show next to the positive button (center button)               |
| `neutral`         | `{ text: string; onPress?: (selectedValue?: string) => void }` | Android  | Button to show in the neutral position (left most button)                |
| `onDismiss`       | `(selectedValue?: string) => void`                             | Android  | Called when dialog is dismissed without pressing a button                |

#### Examples

```typescript
// Basic single choice
Alert.singleChoice(
  'Choose Size',
  [
    { text: 'Small', value: 'S' },
    { text: 'Medium', value: 'M', defaultSelected: true },
    { text: 'Large', value: 'L' },
    { text: 'Extra Large', value: 'XL' },
  ],
  {
    positive: {
      text: 'OK',
      onPress: (selectedValue) => console.log('Selected:', selectedValue),
    },
    negative: {
      text: 'Cancel',
    },
  }
);
```

#### Video Examples

| Android                                                               |
| --------------------------------------------------------------------- |
|https://github.com/user-attachments/assets/c0fe86e0-b33b-4e1d-bf0f-ed8172305ae5|

---

### Alert.multiChoice()

#### Android ONLY ‼️

Display a multi-choice selection dialog with checkboxes.

#### Parameters

| Parameter | Type                       | Required | Description             |
| --------- | -------------------------- | -------- | ----------------------- |
| `title`   | `string`                   | ✅       | The dialog title        |
| `items`   | `Array<MultiChoiceButton>` | ✅       | Array of choice options |
| `options` | `MultiChoiceOptions`       | ❌       | Additional options      |

#### MultiChoiceButton Properties

| Property          | Type      | Platform | Description                                                              |
| ----------------- | --------- | -------- | ------------------------------------------------------------------------ |
| `text`            | `string`  | Android  | Label text shown next to the checkbox                                    |
| `value`           | `string`  | Android  | Unique identifier included in results array when this option is selected |
| `defaultSelected` | `boolean` | Android  | Whether this option should be pre-checked when dialog opens              |

#### MultiChoiceOptions Properties

| Property          | Type                                                              | Platform | Description                                                                           |
| ----------------- | ----------------------------------------------------------------- | -------- | ------------------------------------------------------------------------------------- |
| `cancelable`      | `boolean`                                                         | Android  | When `true`, tapping outside the dialog dismisses it                                  |
| `headerAlignment` | `'left' \| 'center'`                                              | Android  | Horizontal alignment of the title text                                                |
| `positive`        | `{ text: string; onPress?: (selectedValues?: string[]) => void }` | Android  | Button to show in the positive position in the alert (right most button)              |
| `negative`        | `{ text: string; onPress?: (selectedValues?: string[]) => void }` | Android  | Button to show next to the positive button (center button)                            |
| `neutral`         | `{ text: string; onPress?: (selectedValues?: string[]) => void }` | Android  | Button to show in the neutral position (left most button)                             |
| `onDismiss`       | `(selectedValues?: string[]) => void`                             | Android  | Called when dialog is dismissed without pressing a button, receives current selection |

#### Examples

```typescript
// Basic multi choice
Alert.multiChoice(
  'Select Features',
  [
    { text: 'Push Notifications', value: 'push', defaultSelected: true },
    { text: 'Email Updates', value: 'email' },
    { text: 'SMS Alerts', value: 'sms' },
    { text: 'In-App Messages', value: 'in_app', defaultSelected: true },
  ],
  {
    positive: {
      text: 'Save',
      onPress: (selectedValues) =>
        console.log('Selected features:', selectedValues),
    },
    negative: {
      text: 'Cancel',
    },
    neutral: {
      text: 'Select All',
      onPress: (selectedValues) =>
        console.log('Current selection:', selectedValues),
    },
  }
);
```

#### Video Examples

| Android                                                              |
| -------------------------------------------------------------------- |
| https://github.com/user-attachments/assets/1cc0ca39-c39c-4c62-9950-0813e0a355e7|
