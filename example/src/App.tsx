import { Text, View, StyleSheet, TouchableNativeFeedback } from 'react-native';
import Alert from '@nui/alert';

export default function App() {
  return (
    <View style={styles.container}>
      <TouchableNativeFeedback
        onPress={() => {
          Alert.alert(
            'TITLE',
            'MESSAGE',
            [
              {
                position: 'positive',
                text: 'OK',
                onPress: () => console.log('OK Pressed'),
              },
              {
                position: 'negative',
                text: 'Cancel',
                onPress: () => console.log('Cancel Pressed'),
              },
            ],
            {
              headerAlignment: 'center',
              cancelable: true,
              onDismiss: () => console.log('Alert dismissed'),
              icon: 'outline_bedtime_24', // Replace with your icon name
            }
          );
        }}
      >
        <Text>Alert</Text>
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
