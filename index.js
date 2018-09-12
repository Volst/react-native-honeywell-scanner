const ReactNative = require('react-native');
const { NativeModules, DeviceEventEmitter } = ReactNative;
const HoneywellScanner = NativeModules.HoneywellScanner;

console.log('SCANNER v9', NativeModules.HoneywellScanner);

/**
 * Listen for available events
 * @param  {String} eventName Name of event one of connectionSuccess, connectionLost, data, rawData
 * @param  {Function} handler Event handler
 */
HoneywellScanner.on = (eventName, handler) => {
  DeviceEventEmitter.addListener(eventName, handler);
};

/**
 * Stop listening for event
 * @param  {String} eventName Name of event one of connectionSuccess, connectionLost, data, rawData
 * @param  {Function} handler Event handler
 */
HoneywellScanner.removeListener = (eventName, handler) => {
  DeviceEventEmitter.removeListener(eventName, handler);
};

module.exports = HoneywellScanner;
