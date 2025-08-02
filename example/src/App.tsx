import { Text, View, StyleSheet, Pressable } from 'react-native';
import Alert from '@nui/alert';

// Button component for consistent styling
function AlertButton({
  title,
  onPress,
  backgroundColor = '#007AFF',
}: {
  title: string;
  onPress: () => void;
  backgroundColor?: string;
}) {
  return (
    <View style={{ borderRadius: 12, overflow: 'hidden' }}>
      <Pressable
        onPress={onPress}
        style={[{ backgroundColor }, styles.buttonContainer]}
        android_ripple={{ color: '#00000040', foreground: true }}
      >
        <Text style={styles.buttonText}>{title}</Text>
      </Pressable>
    </View>
  );
}

// Prompt Alert Component
function PromptAlertButton() {
  return (
    <AlertButton
      title="Prompt"
      backgroundColor="#6366F1" // Indigo
      onPress={() => {
        Alert.prompt(
          'Add new song',
          undefined,
          [
            {
              position: 'positive',
              text: 'Save',
              onPress: () => console.log('OK Pressed'),
            },
            {
              position: 'negative',
              text: 'Cancel',
              onPress: () => console.log('Second Pressed'),
            },
          ],
          'plain-text',
          undefined,
          undefined,
          {
            headerAlignment: 'center',
            icon: 'baseline_audiotrack_24',
            cancelable: true,
            onDismiss: () => console.log('Dismissed'),
            placeholder: 'Name',
          }
        );
      }}
    />
  );
}

// Basic Alert Component
function BasicAlertButton() {
  return (
    <AlertButton
      title="Alert"
      backgroundColor="#EF4444" // Red
      onPress={() => {
        Alert.alert(
          'Bedtime alert!',
          "It's time to start winding down",
          [
            {
              position: 'positive',
              text: 'Okay',
              onPress: () => console.log('OK Pressed'),
            },
            {
              position: 'negative',
              text: 'Extend 30 minutes',
              onPress: () => console.log('Extend Pressed'),
            },
          ],
          {
            headerAlignment: 'center',
            icon: 'baseline_bedtime_24',
            cancelable: true,
            onDismiss: () => console.log('Dismissed'),
            userInterfaceStyle: 'light',
          }
        );
      }}
    />
  );
}

// Items Alert Component
function ItemsAlertButton() {
  return (
    <AlertButton
      title="Items"
      backgroundColor="#10B981" // Emerald
      onPress={() => {
        Alert.items(
          'Dev options',
          [
            {
              text: 'Turn on dark mode',
              onPress: () => console.log('Turn on dark mode'),
            },
            {
              text: 'Inspect the network',
              onPress: () => console.log('Inspect the network'),
            },
            {
              text: 'Highlight rerenders',
              onPress: () => console.log('Highlight rerenders'),
            },
          ],
          {
            headerAlignment: 'left',
            cancelable: true,
            onDismiss: () => console.log('Dismissed'),
            presentation: 'alert',
            cancelButtonText: 'Close',
            message: 'This is a message',
          }
        );
      }}
    />
  );
}

// Single Choice Alert Component
function SingleChoiceAlertButton() {
  return (
    <AlertButton
      title="Single Choice"
      backgroundColor="#8B5CF6" // Violet
      onPress={() => {
        Alert.singleChoice(
          'Choose your theme',
          [
            { text: 'Light', value: 'light', defaultSelected: true },
            { text: 'Dark', value: 'dark' },
            { text: 'System', value: 'system' },
          ],
          {
            headerAlignment: 'center',
            cancelable: true,
            icon: 'baseline_bedtime_24',
            onDismiss: () => console.log('Dismissed'),
            positive: {
              text: 'Confirm',
              onPress: (value) => console.log('Selected value:', value),
            },
            negative: {
              text: 'Nevermind',
              onPress: (value) => console.log('Selected value:', value),
            },
          }
        );
      }}
    />
  );
}

// Multi Choice Alert Component
function MultiChoiceAlertButton() {
  return (
    <AlertButton
      title="Multi Choice"
      backgroundColor="#F59E0B" // Amber
      onPress={() => {
        Alert.multiChoice(
          'Choose your favorite foods',
          [
            { text: 'Pizza', value: 'pizza', defaultSelected: true },
            { text: 'Burger', value: 'burger' },
            { text: 'Sushi', value: 'sushi' },
            { text: 'Salad', value: 'salad', defaultSelected: true },
            { text: 'Pasta', value: 'pasta' },
            { text: 'Ice Cream', value: 'ice-cream' },
          ],
          {
            headerAlignment: 'center',
            cancelable: true,
            icon: 'baseline_cookie_24',
            onDismiss: () => console.log('Dismissed'),
            positive: {
              text: 'Confirm',
              onPress: (values) => console.log('Selected values:', values),
            },
            negative: {
              text: 'Nevermind',
              onPress: (values) => console.log('Selected values:', values),
            },
          }
        );
      }}
    />
  );
}

export default function App() {
  return (
    <View style={styles.container}>
      <PromptAlertButton />
      <BasicAlertButton />
      <ItemsAlertButton />
      <SingleChoiceAlertButton />
      <MultiChoiceAlertButton />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#F8FAFC',
    gap: 16,
    padding: 20,
  },
  buttonContainer: {
    paddingHorizontal: 24,
    paddingVertical: 14,
    borderRadius: 12,
    minWidth: 160,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 3.84,
    elevation: 5,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 16,
    fontWeight: '600',
    letterSpacing: 0.5,
  },
  indicator: {
    marginVertical: 10,
  },
});
