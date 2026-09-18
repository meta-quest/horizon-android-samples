/*
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.example.metavrx.uiset.gallery.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.metavrx.uiset.gallery.ui.Demo
import com.example.metavrx.uiset.gallery.ui.Screen
import metavrx.uiset.compose.Icon
import metavrx.uiset.compose.Text
import metavrx.uiset.compose.theme.UiSetTheme
import metavrx.uiset.compose.theme.icons.Icons

/**
 * Every icon in `Icons.Regular`.
 *
 * This is a `@Composable` FUNCTION, not a top-level `val`. Each icon property has a `@Composable`
 * getter that resolves a vector resource, so the list can only be built during composition — a
 * top-level `val` fails with "@Composable invocations can only happen from the context of
 * a @Composable function".
 *
 * Generated from the `val Icons.Regular.<Name>` declarations in the library, NOT from filenames,
 * which do not always match: `10sBackward.kt` declares `TenSecondsBackward`, and two others differ
 * the same way because a Kotlin identifier cannot start with a digit. There is no runtime registry
 * to enumerate and reflection would be stripped by R8, so the list is spelled out. Regenerate if
 * the icon set changes.
 */
@Composable
private fun allIcons(): List<Pair<String, ImageVector>> = listOf(
    "Add" to Icons.Regular.Add,
    "Apps" to Icons.Regular.Apps,
    "ArrowDown" to Icons.Regular.ArrowDown,
    "ArrowDownCircle" to Icons.Regular.ArrowDownCircle,
    "ArrowLeft" to Icons.Regular.ArrowLeft,
    "ArrowLeftCircle" to Icons.Regular.ArrowLeftCircle,
    "ArrowRight" to Icons.Regular.ArrowRight,
    "ArrowRightCircle" to Icons.Regular.ArrowRightCircle,
    "ArrowsLeftRight" to Icons.Regular.ArrowsLeftRight,
    "ArrowsUpDown" to Icons.Regular.ArrowsUpDown,
    "ArrowUp" to Icons.Regular.ArrowUp,
    "ArrowUpCircle" to Icons.Regular.ArrowUpCircle,
    "Basic" to Icons.Regular.Basic,
    "Bluetooth" to Icons.Regular.Bluetooth,
    "Bookmark" to Icons.Regular.Bookmark,
    "BookmarkAdd" to Icons.Regular.BookmarkAdd,
    "BrightnessLow" to Icons.Regular.BrightnessLow,
    "BrightnessMid" to Icons.Regular.BrightnessMid,
    "BrightnessOff" to Icons.Regular.BrightnessOff,
    "BrightnessOn" to Icons.Regular.BrightnessOn,
    "BtA" to Icons.Regular.BtA,
    "BtB" to Icons.Regular.BtB,
    "BtMenu" to Icons.Regular.BtMenu,
    "BtOculus" to Icons.Regular.BtOculus,
    "BtX" to Icons.Regular.BtX,
    "BtY" to Icons.Regular.BtY,
    "BulletList" to Icons.Regular.BulletList,
    "CameraRoll" to Icons.Regular.CameraRoll,
    "CategoryAll" to Icons.Regular.CategoryAll,
    "Chat" to Icons.Regular.Chat,
    "ChatEllipses" to Icons.Regular.ChatEllipses,
    "ChatOff" to Icons.Regular.ChatOff,
    "ChatText" to Icons.Regular.ChatText,
    "CheckAlt" to Icons.Regular.CheckAlt,
    "CheckboxCircle" to Icons.Regular.CheckboxCircle,
    "CheckCircle" to Icons.Regular.CheckCircle,
    "ChevronDown" to Icons.Regular.ChevronDown,
    "ChevronLeft" to Icons.Regular.ChevronLeft,
    "ChevronRight" to Icons.Regular.ChevronRight,
    "ChevronUp" to Icons.Regular.ChevronUp,
    "Close" to Icons.Regular.Close,
    "CloseCircle" to Icons.Regular.CloseCircle,
    "ClosedCaptioning" to Icons.Regular.ClosedCaptioning,
    "ClosedCaptioningOff" to Icons.Regular.ClosedCaptioningOff,
    "Clothing" to Icons.Regular.Clothing,
    "Cloud" to Icons.Regular.Cloud,
    "Color" to Icons.Regular.Color,
    "Comfortable" to Icons.Regular.Comfortable,
    "CommandCenter" to Icons.Regular.CommandCenter,
    "Compass" to Icons.Regular.Compass,
    "Compose" to Icons.Regular.Compose,
    "Computer" to Icons.Regular.Computer,
    "ConceptsDogfooding" to Icons.Regular.ConceptsDogfooding,
    "Couch" to Icons.Regular.Couch,
    "CreditCard" to Icons.Regular.CreditCard,
    "Desktop" to Icons.Regular.Desktop,
    "DesktopOff" to Icons.Regular.DesktopOff,
    "Distance" to Icons.Regular.Distance,
    "DoNotDisturb" to Icons.Regular.DoNotDisturb,
    "DoNotDisturbOff" to Icons.Regular.DoNotDisturbOff,
    "Download" to Icons.Regular.Download,
    "Environment" to Icons.Regular.Environment,
    "Error" to Icons.Regular.Error,
    "ErrorCircle" to Icons.Regular.ErrorCircle,
    "Events" to Icons.Regular.Events,
    "EventsAdd" to Icons.Regular.EventsAdd,
    "File" to Icons.Regular.File,
    "Filter" to Icons.Regular.Filter,
    "Flip" to Icons.Regular.Flip,
    "Folder" to Icons.Regular.Folder,
    "Frequency" to Icons.Regular.Frequency,
    "FriendsAdd" to Icons.Regular.FriendsAdd,
    "FriendsBlock" to Icons.Regular.FriendsBlock,
    "FriendsExcept" to Icons.Regular.FriendsExcept,
    "FriendsKick" to Icons.Regular.FriendsKick,
    "FriendsRemove" to Icons.Regular.FriendsRemove,
    "FriendsReport" to Icons.Regular.FriendsReport,
    "FriendsRequestSent" to Icons.Regular.FriendsRequestSent,
    "FullScreen" to Icons.Regular.FullScreen,
    "FullScreenExit" to Icons.Regular.FullScreenExit,
    "GalleryConnect" to Icons.Regular.GalleryConnect,
    "GalleryFolder" to Icons.Regular.GalleryFolder,
    "GalleryPhone" to Icons.Regular.GalleryPhone,
    "GalleryReady" to Icons.Regular.GalleryReady,
    "GalleryReconnect" to Icons.Regular.GalleryReconnect,
    "Gamepad" to Icons.Regular.Gamepad,
    "HandCursor" to Icons.Regular.HandCursor,
    "HeadsetCasting" to Icons.Regular.HeadsetCasting,
    "HeartOff" to Icons.Regular.HeartOff,
    "HeartOn" to Icons.Regular.HeartOn,
    "History" to Icons.Regular.History,
    "Home" to Icons.Regular.Home,
    "HomeEdit" to Icons.Regular.HomeEdit,
    "Horizon" to Icons.Regular.Horizon,
    "Ibeam" to Icons.Regular.Ibeam,
    "Image" to Icons.Regular.Image,
    "Images360" to Icons.Regular.Images360,
    "Info" to Icons.Regular.Info,
    "Intense" to Icons.Regular.Intense,
    "Internet" to Icons.Regular.Internet,
    "IntrusionDetection" to Icons.Regular.IntrusionDetection,
    "Keyboard" to Icons.Regular.Keyboard,
    "KeyboardEnter" to Icons.Regular.KeyboardEnter,
    "KeyboardOff" to Icons.Regular.KeyboardOff,
    "KeyboardOn" to Icons.Regular.KeyboardOn,
    "KeyboardSpace" to Icons.Regular.KeyboardSpace,
    "LeavePlaces" to Icons.Regular.LeavePlaces,
    "LensAdjustment" to Icons.Regular.LensAdjustment,
    "Library" to Icons.Regular.Library,
    "Linkedin" to Icons.Regular.Linkedin,
    "ListChecked" to Icons.Regular.ListChecked,
    "ListLock" to Icons.Regular.ListLock,
    "ListSort" to Icons.Regular.ListSort,
    "ListView" to Icons.Regular.ListView,
    "LockOff" to Icons.Regular.LockOff,
    "LockOn" to Icons.Regular.LockOn,
    "Media180" to Icons.Regular.Media180,
    "Media1803d" to Icons.Regular.Media1803d,
    "Media2d" to Icons.Regular.Media2d,
    "Media360" to Icons.Regular.Media360,
    "Media3603d" to Icons.Regular.Media3603d,
    "Media3dHoriz" to Icons.Regular.Media3dHoriz,
    "Media3dVert" to Icons.Regular.Media3dVert,
    "MediaImmersivePhoto" to Icons.Regular.MediaImmersivePhoto,
    "MediaImmersiveVideo" to Icons.Regular.MediaImmersiveVideo,
    "Messenger" to Icons.Regular.Messenger,
    "MicrophoneOff" to Icons.Regular.MicrophoneOff,
    "MicrophoneOn" to Icons.Regular.MicrophoneOn,
    "MicrophoneUnavailable" to Icons.Regular.MicrophoneUnavailable,
    "Minimize" to Icons.Regular.Minimize,
    "Mobile" to Icons.Regular.Mobile,
    "MobileKeyboard" to Icons.Regular.MobileKeyboard,
    "Moderate" to Icons.Regular.Moderate,
    "Molokini" to Icons.Regular.Molokini,
    "MoreHorizontal" to Icons.Regular.MoreHorizontal,
    "MoreVertical" to Icons.Regular.MoreVertical,
    "Move" to Icons.Regular.Move,
    "MultiBrowser" to Icons.Regular.MultiBrowser,
    "MyMedia" to Icons.Regular.MyMedia,
    "NightMode" to Icons.Regular.NightMode,
    "Notifications" to Icons.Regular.Notifications,
    "NotificationsOff" to Icons.Regular.NotificationsOff,
    "OculusRemote" to Icons.Regular.OculusRemote,
    "OculusVoice" to Icons.Regular.OculusVoice,
    "OculusVoiceOff" to Icons.Regular.OculusVoiceOff,
    "OpenCircle" to Icons.Regular.OpenCircle,
    "OpenPanel" to Icons.Regular.OpenPanel,
    "OpenTab" to Icons.Regular.OpenTab,
    "Parties" to Icons.Regular.Parties,
    "Password" to Icons.Regular.Password,
    "PasswordHidden" to Icons.Regular.PasswordHidden,
    "PasswordVisible" to Icons.Regular.PasswordVisible,
    "Pause" to Icons.Regular.Pause,
    "PauseCircle" to Icons.Regular.PauseCircle,
    "Phone" to Icons.Regular.Phone,
    "PhysicalFeatures" to Icons.Regular.PhysicalFeatures,
    "Play" to Icons.Regular.Play,
    "PlayCircle" to Icons.Regular.PlayCircle,
    "PlayNext" to Icons.Regular.PlayNext,
    "PlayNextCircle" to Icons.Regular.PlayNextCircle,
    "PlayPrev" to Icons.Regular.PlayPrev,
    "PlayPrevCircle" to Icons.Regular.PlayPrevCircle,
    "Power" to Icons.Regular.Power,
    "Privacy" to Icons.Regular.Privacy,
    "Profile" to Icons.Regular.Profile,
    "ProfileCircle" to Icons.Regular.ProfileCircle,
    "Purchase" to Icons.Regular.Purchase,
    "Question" to Icons.Regular.Question,
    "RecentlyPlayed" to Icons.Regular.RecentlyPlayed,
    "Redo" to Icons.Regular.Redo,
    "Refresh" to Icons.Regular.Refresh,
    "RefreshCircle" to Icons.Regular.RefreshCircle,
    "Refund" to Icons.Regular.Refund,
    "RemoveCircle" to Icons.Regular.RemoveCircle,
    "Reorient" to Icons.Regular.Reorient,
    "Replay" to Icons.Regular.Replay,
    "ReplayCircle" to Icons.Regular.ReplayCircle,
    "ResizeHorizontal" to Icons.Regular.ResizeHorizontal,
    "ResizeHorizontalDown" to Icons.Regular.ResizeHorizontalDown,
    "ResizeVerticalDown" to Icons.Regular.ResizeVerticalDown,
    "ResizeVerticalUp" to Icons.Regular.ResizeVerticalUp,
    "RewardPacks" to Icons.Regular.RewardPacks,
    "RotateLeft" to Icons.Regular.RotateLeft,
    "RotateRight" to Icons.Regular.RotateRight,
    "Scale" to Icons.Regular.Scale,
    "ScaleDown" to Icons.Regular.ScaleDown,
    "Screenshot" to Icons.Regular.Screenshot,
    "Search" to Icons.Regular.Search,
    "Send" to Icons.Regular.Send,
    "Settings" to Icons.Regular.Settings,
    "SidebarPin" to Icons.Regular.SidebarPin,
    "SidebarPinClose" to Icons.Regular.SidebarPinClose,
    "Sitting" to Icons.Regular.Sitting,
    "Standing" to Icons.Regular.Standing,
    "Star" to Icons.Regular.Star,
    "StarFull" to Icons.Regular.StarFull,
    "StarHalf" to Icons.Regular.StarHalf,
    "Stationary" to Icons.Regular.Stationary,
    "Stop" to Icons.Regular.Stop,
    "StopCircle" to Icons.Regular.StopCircle,
    "Storage" to Icons.Regular.Storage,
    "StorageSd" to Icons.Regular.StorageSd,
    "Store" to Icons.Regular.Store,
    "StoreCart" to Icons.Regular.StoreCart,
    "Sync" to Icons.Regular.Sync,
    "SyncCircle" to Icons.Regular.SyncCircle,
    "SyncOff" to Icons.Regular.SyncOff,
    "Table" to Icons.Regular.Table,
    "Tag" to Icons.Regular.Tag,
    "Television" to Icons.Regular.Television,
    "TenSecondsBackward" to Icons.Regular.TenSecondsBackward,
    "TenSecondsForward" to Icons.Regular.TenSecondsForward,
    "ThirteenPlus" to Icons.Regular.ThirteenPlus,
    "ThreePeople" to Icons.Regular.ThreePeople,
    "ThumbsDown" to Icons.Regular.ThumbsDown,
    "ThumbsUp" to Icons.Regular.ThumbsUp,
    "Time" to Icons.Regular.Time,
    "Tips" to Icons.Regular.Tips,
    "ToTop" to Icons.Regular.ToTop,
    "Touch2Left" to Icons.Regular.Touch2Left,
    "Touch2Right" to Icons.Regular.Touch2Right,
    "TouchLeft" to Icons.Regular.TouchLeft,
    "Touchpad" to Icons.Regular.Touchpad,
    "TouchRight" to Icons.Regular.TouchRight,
    "Trash" to Icons.Regular.Trash,
    "Trophy" to Icons.Regular.Trophy,
    "Twitter" to Icons.Regular.Twitter,
    "Undo" to Icons.Regular.Undo,
    "UniversalMenu" to Icons.Regular.UniversalMenu,
    "UnknownController" to Icons.Regular.UnknownController,
    "UnknownHeadset" to Icons.Regular.UnknownHeadset,
    "UnknownSources" to Icons.Regular.UnknownSources,
    "UnlockPattern" to Icons.Regular.UnlockPattern,
    "Unrated" to Icons.Regular.Unrated,
    "Update" to Icons.Regular.Update,
    "UppercaseArrow" to Icons.Regular.UppercaseArrow,
    "UsbStick" to Icons.Regular.UsbStick,
    "Vibration" to Icons.Regular.Vibration,
    "VideoCapture" to Icons.Regular.VideoCapture,
    "ViewGallery" to Icons.Regular.ViewGallery,
    "VisitHome" to Icons.Regular.VisitHome,
    "VoiceCommand" to Icons.Regular.VoiceCommand,
    "VolumeLow" to Icons.Regular.VolumeLow,
    "VolumeMid" to Icons.Regular.VolumeMid,
    "VolumeOff" to Icons.Regular.VolumeOff,
    "VolumeOn" to Icons.Regular.VolumeOn,
    "VrObject" to Icons.Regular.VrObject,
    "Wallet" to Icons.Regular.Wallet,
    "Warning" to Icons.Regular.Warning,
    "WidthExtrawide" to Icons.Regular.WidthExtrawide,
    "WidthMedium" to Icons.Regular.WidthMedium,
    "WidthSmall" to Icons.Regular.WidthSmall,
    "WidthWide" to Icons.Regular.WidthWide,
    "WifiAltLow" to Icons.Regular.WifiAltLow,
    "WifiAltMid" to Icons.Regular.WifiAltMid,
    "WifiAltOff" to Icons.Regular.WifiAltOff,
    "WifiOff" to Icons.Regular.WifiOff,
    "WifiOn" to Icons.Regular.WifiOn,
    "WifiSecure" to Icons.Regular.WifiSecure,
    "Workplace" to Icons.Regular.Workplace,
    "World" to Icons.Regular.World,
    "Zoom" to Icons.Regular.Zoom,
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IconsScreen() {
  val icons = allIcons()
  Screen {
    Demo("Regular", "${icons.size} icons, each an ImageVector backed by a vector drawable.") {
      FlowRow(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(4.dp),
          verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        icons.forEach { (name, vector) -> IconCell(name, vector) }
      }
    }
  }
}

@Composable
private fun IconCell(name: String, vector: ImageVector) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(88.dp)) {
    Icon(imageVector = vector, contentDescription = name, modifier = Modifier.size(28.dp))
    Spacer(Modifier.height(6.dp))
    Text(
        text = name,
        style = UiSetTheme.typography.bodySmall,
        color = UiSetTheme.colorScheme.background.content.secondary,
        textAlign = TextAlign.Center,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
    )
  }
}
