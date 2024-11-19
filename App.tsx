import {useEffect} from 'react';
import {View, StyleSheet, Text, NativeEventEmitter, Button} from 'react-native';
import MyNativeModule from './src/MyNativeModule';
import React from 'react';
import NativeScreenModule from './src/NativeScreenModule';

const App = () => {
  useEffect(() => {
    MyNativeModule.showToast('asdasd');

    setTimeout(() => {
      // MyNativeModule.getCurrentTimeWithPromise()
      //   .then(currentTime => {
      //     console.log('Current Time:', currentTime);
      //   })
      //   .catch(error => {
      //     console.error('Error getting time:', error);
      //   });
      // const value = MyNativeModule.getCurrentTime();
      // alert(value);
      // MyNativeModule.showAlert('ádkajshdakjshd', 'mesagae')
      //   .then(value => console.log(value))
      //   .catch(error => console.log(error));
    }, 3000);

    const eventEmitter = new NativeEventEmitter(MyNativeModule);

    // Đăng ký lắng nghe sự kiện
    const subscription = eventEmitter.addListener('MyEventName', eventData => {
      console.log('Received event:', eventData);
    });

    return () => {
      subscription.remove();
    };
  }, []);

  const handleOpenNativeScreen = async () => {
    try {
      const result = await NativeScreenModule.openNativeInputScreen();
      console.log('Result from Native:', result);
    } catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <View style={styles.container}>
      <Text>asdasdasd</Text>
      <Button
        title="Press me"
        onPress={() => {
          MyNativeModule.triggerEvent('MyEventName', 'ahihihihihi');
        }}
      />
      <View style={{height: 30}} />
      <Button
        title="Show Native Screen"
        onPress={() => {
          MyNativeModule.showNativeScreen('ahahahahahaha');
        }}
      />
      <View style={{height: 30}} />
      <Button
        title="Show Native Input Screen"
        onPress={handleOpenNativeScreen}
      />
      {/* <NativeListView
        style={styles.list}
        items={['Item 1', 'Item 2', 'Item 3']}
      /> */}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {flex: 1},
  list: {width: '100%', height: 400},
});

export default App;
