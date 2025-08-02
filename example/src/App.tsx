import {
  Text,
  View,
  StyleSheet,
  TouchableNativeFeedback,
  ActionSheetIOS,
  ActivityIndicator,
  // Alert,
} from 'react-native';
import Alert from '@nui/alert';

export default function App() {
  return (
    <View style={styles.container}>
      <TouchableNativeFeedback
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
            'login-password',
            undefined,
            undefined,
            {
              headerAlignment: 'center',
              icon: 'baseline_audiotrack_24',
              cancelable: true,
              placeholder: 'Song name...',
              onDismiss: () => console.log('Dismissed'),
            }
          );
        }}
      >
        <Text>Prompt</Text>
      </TouchableNativeFeedback>
      <TouchableNativeFeedback
        style={{ padding: 20 }}
        onPress={() => {
          Alert.alert(
            'Oops',
            'Something went wrong',
            [
              {
                position: 'positive',
                text: 'Okay',
                onPress: () => console.log('OK Pressed'),
              },
              {
                position: 'negative',
                text: 'Cancel',
                onPress: () => console.log('Second Pressed'),
              },
              {
                position: 'neutral',
                text: 'Neutral',
                onPress: () => console.log('Second Pressed'),
              },
            ],
            {
              headerAlignment: 'center',
              icon: 'baseline_audiotrack_24',
              cancelable: true,
              onDismiss: () => console.log('Dismissed'),
              userInterfaceStyle: 'light',
            }
          );
        }}
      >
        <Text>Alert</Text>
      </TouchableNativeFeedback>
      <TouchableNativeFeedback
        style={{ padding: 20 }}
        onPress={() => {
          Alert.items(
            'Choose an option',
            [
              {
                text: 'First Option',
                isPreferred: true,
                onPress: () => console.log('OK Pressed'),
              },
              {
                text: 'Second Option',
                onPress: () => console.log('Second Pressed'),
              },
              {
                text: 'Third option',
                onPress: () => console.log('Third Pressed'),
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
      >
        <Text>Items</Text>
      </TouchableNativeFeedback>
      <ActivityIndicator />
      <TouchableNativeFeedback
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
      >
        <Text>Single Choice</Text>
      </TouchableNativeFeedback>
      <TouchableNativeFeedback
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
      >
        <Text>Multi Choice</Text>
      </TouchableNativeFeedback>
      <TouchableNativeFeedback
        onPress={() =>
          ActionSheetIOS.showActionSheetWithOptions(
            {
              options: ['Cancel', 'Option 1', 'Option 2'],
              cancelButtonIndex: 1,
              destructiveButtonIndex: 2,
            },
            () => {}
          )
        }
      >
        <Text>Action sheet</Text>
      </TouchableNativeFeedback>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#fff',
  },
});
