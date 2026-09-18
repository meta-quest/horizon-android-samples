# GalleryVrx - UI Set Component Gallery

GalleryVrx is a Jetpack Compose catalog for the Meta VR UI Set. Browse component families from
the navigation rail, filter the gallery with search, and switch between light and dark themes to
see each example update in place.

The sample imports `com.meta.metavrx:metavrx-bom` and declares
`com.meta.metavrx.uiset:uiset-compose-compat` without a version, so it follows
the validated MetaVRX release set.

The app brings together every public composable in the UI Set component packages, along with the
complete semantic type scale and regular icon collection. Theme Lab provides interactive examples
of density, color, and local component customization.

## Patterns demonstrated

- Buttons and navigation with `LabelButton`, `IconButton`, `ButtonShelf`, `TextTileButton`, and
  `SideNavItem`
- Primary, secondary, and outlined cards
- Selection controls and sliders across their supported styles and sizes
- Dialogs, dropdowns, tooltips, text input, search, and live field validation
- Standard and compact density with light and dark color schemes
- Local component overrides
- The full semantic type scale and a searchable `Icons.Regular` collection
