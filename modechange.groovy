/**
 *  Mode Notifications
 *
 *  Copyright 2015 Dav Glass
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "Mode Notifications",
    namespace: "davglass",
    author: "Dav Glass",
    description: "Push Notification on Mode Change",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Mode change alerts") {
        paragraph "No settings really, just sends a push notification on Mode Change"
    }
}

def installed() {
    log.debug "Installed with settings: ${settings}"
    initialize()
}

def updated() {
    log.debug "Updated with settings: ${settings}"
    unsubscribe()
    initialize()
}

def initialize() {
    state.lastMode = location.mode;
    subscribe(location, handler);
}

def handler(evt) {
    if (evt.isStateChange()) {
        def msg = "Mode changed from ${state.lastMode} to ${evt.value}."
        log.debug msg;
        sendNotificationEvent(msg);
        sendPush(msg);
    }
    state.lastMode = evt.value; 
}
