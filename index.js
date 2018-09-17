const ReactNative = require('react-native');
const { NativeModules, DeviceEventEmitter } = ReactNative;
const HoneywellScanner = NativeModules.HoneywellScanner || {}; // Hacky fallback for iOS

const allowedEvents = [
  HoneywellScanner.BARCODE_READ_SUCCESS,
  HoneywellScanner.BARCODE_READ_FAIL,
];

/**
 * Listen for available events
 * @param  {String} eventName Name of event one of barcodeReadSuccess, barcodeReadFail
 * @param  {Function} handler Event handler
 */
HoneywellScanner.on = (eventName, handler) => {
  if (!allowedEvents.includes(eventName)) {
    throw new Error(`Event name ${eventName} is not a supported event.`);
  }
  DeviceEventEmitter.addListener(eventName, handler);
};

/**
 * Stop listening for event
 * @param  {String} eventName Name of event one of barcodeReadSuccess, barcodeReadFail
 * @param  {Function} handler Event handler
 */
HoneywellScanner.off = (eventName, handler) => {
  if (!allowedEvents.includes(eventName)) {
    throw new Error(`Event name ${eventName} is not a supported event.`);
  }
  DeviceEventEmitter.removeListener(eventName, handler);
};

module.exports = HoneywellScanner;
