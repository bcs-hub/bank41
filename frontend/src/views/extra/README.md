# Extra Views - Advanced Mapping Components

This folder contains experimental and demonstration mapping features built with Vue 3 and Leaflet.

## MapCountyView.vue

### Overview
An interactive map component that displays Estonian counties using real-time data from OpenStreetMap via the Overpass API. Features dynamic county boundaries, hover effects, click selection, and automatic data export functionality.

### Key Features

#### Interactive County Visualization
- **Dynamic Data Loading**: Fetches current county boundary data from OpenStreetMap using Overpass API
- **Smart Filtering**: Automatically identifies Estonian counties using name patterns and administrative levels
- **Visual Styling**: Each county gets a unique color from a rotating palette based on name hash
- **Hover Effects**: Counties highlight on mouseover with enhanced styling and tooltips
- **Click Selection**: Click any county to view detailed administrative information

#### UI Controls
- **Reset View**: Returns map to default zoom level (7) and center coordinates (58.7°N, 25.3°E)
- **Toggle Labels**: Shows/hides permanent county name labels at calculated centroids  
- **Reload Data**: Refreshes county data from Overpass API with loading indicators
- **Loading States**: Full-screen overlay with progress messages during data fetching

#### Data Management
- **Automatic Export**: Downloads fetched GeoJSON data as `geo-Json-Data.json` file
- **Error Handling**: Comprehensive error messages for timeouts, rate limits, and server issues
- **Data Validation**: Filters and validates county data with detailed console logging
- **Caching**: Data persists until manual reload (no automatic refresh)

### Technical Architecture

#### Dependencies
```javascript
import { LMap, LTileLayer, LGeoJson, LMarker, LTooltip } from '@vue-leaflet/vue-leaflet'
import osmtogeojson from 'osmtogeojson'
```

#### Data Flow
1. **API Query**: Constructs Overpass API query targeting Estonian administrative boundaries
2. **Data Conversion**: Converts OSM XML/JSON to GeoJSON using `osmtogeojson` library
3. **Filtering**: Applies multiple filters to identify genuine Estonian counties
4. **Rendering**: Creates Leaflet GeoJSON layer with interactive features
5. **Labeling**: Calculates county centroids and generates permanent labels

#### Overpass API Query Structure
```javascript
const overpassQuery = `
  [out:json][timeout:25];
  (
    relation["admin_level"="4"]["boundary"="administrative"]
      ["name"~"maa$|County|maakond"]
      (bbox:57.5,21.5,59.7,28.5);
    relation["admin_level"="6"]["boundary"="administrative"]
      ["name"~"maa$|County|maakond"]  
      (bbox:57.5,21.5,59.7,28.5);
    relation["place"~"island|region"]["name"~"maa$"]
      (bbox:57.5,21.5,59.7,28.5);
  );
  out geom;
`
```

#### County Filtering Logic
The component uses multiple criteria to identify Estonian counties:

**Name Patterns:**
- Ends with "maa" (Estonian county suffix)
- Ends with "County" (English equivalent)
- Contains "maakond" (Estonian administrative term)
- Matches hardcoded list of known Estonian counties

**Administrative Criteria:**
- `admin_level` 4 or 6 (county-level administrative divisions)
- `boundary="administrative"` (administrative boundaries)
- `place` values of "island" or "region" for special cases

**Geographic Validation:**
- Bounding box: 57.5°N-59.7°N, 21.5°E-28.5°E (Estonia extent)
- Excludes neighboring countries by ISO code patterns
- Validates geometry types (Polygon or MultiPolygon)

### Component Data Structure

#### State Management
```javascript
data() {
  return {
    // Map Configuration
    zoom: 7,
    center: [58.7, 25.3], // Estonia center
    mapOptions: { zoomControl: true, scrollWheelZoom: true },
    
    // Data
    countyData: null,        // GeoJSON FeatureCollection
    countyLabels: [],        // Array of {name, center} objects
    selectedCounty: null,    // Currently selected county feature
    
    // UI State  
    loading: false,
    error: null,
    showLabels: true,
    loadingMessage: 'Fetching county data...',
    
    // API Configuration
    overpassUrl: 'https://overpass-api.de/api/interpreter'
  }
}
```

#### GeoJSON Styling Options
```javascript
geoJsonOptions: {
  style: (feature) => ({
    fillColor: this.getCountyColor(feature),
    weight: 2,
    opacity: 1, 
    color: '#2c3e50',
    fillOpacity: 0.3
  }),
  onEachFeature: (feature, layer) => {
    layer.on({
      mouseover: this.highlightFeature,
      mouseout: this.resetHighlight,
      click: this.selectCounty
    });
  }
}
```

### Key Methods

#### `loadCountyData()`
- **Purpose**: Fetches and processes Estonian county data from Overpass API
- **Process**: Constructs query → Makes HTTP request → Converts to GeoJSON → Filters results → Updates state
- **Error Handling**: Timeout detection, rate limit handling, server error management
- **Debugging**: Extensive console logging for data validation and troubleshooting

#### `generateCountyLabels()`  
- **Purpose**: Creates permanent labels positioned at county centroids
- **Algorithm**: Calculates geometric center for each county polygon/multipolygon
- **Output**: Array of `{name, center}` objects for label positioning

#### `getFeatureCenter(geometry)`
- **Purpose**: Calculates centroid coordinates for complex polygon geometries
- **Handles**: Both Polygon and MultiPolygon geometry types
- **Algorithm**: For MultiPolygon, finds largest polygon then calculates centroid
- **Returns**: `[latitude, longitude]` array or `null` on error

#### `getCountyColor(feature)`
- **Purpose**: Generates consistent colors for counties based on name
- **Algorithm**: String hash of county name modulo color array length
- **Palette**: 7 predefined colors for visual variety

### Error Handling

#### API Error Types
- **Timeout (30s)**: "Request timed out. The Overpass API might be busy. Please try again."
- **Rate Limiting (429)**: "Too many requests. Please wait a moment and try again."
- **Server Errors (5xx)**: "Server error from Overpass API. Please try again later."
- **Generic Errors**: Custom error message or fallback text

#### Data Validation
- **No Features**: "No administrative boundary data received from the API"
- **No Estonian Counties**: "No Estonian county candidates found. Total features received: X"
- **Geometry Errors**: Console warnings for centroid calculation failures

### Styling & UI

#### Responsive Design
- **Mobile Layout**: Stacked controls, centered buttons, reduced map height (500px)
- **Desktop Layout**: Horizontal control layout with flexible spacing
- **Loading Overlay**: Centered modal with spinner and progress messages

#### County Label Styling
```css
:deep(.county-label) {
  background: rgba(255, 255, 255, 0.9) !important;
  border: 1px solid #ddd !important;
  border-radius: 4px !important;
  font-weight: 600 !important;
  font-size: 11px !important;
  padding: 3px 6px !important;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2) !important;
}
```

### Development Notes

#### TODO Comments in Code
- Line 228: `todo: create separate method "constructOverpassQuery()"`
- Line 254: `todo: create separate method "handleOverpassResponse(response)"`  
- Line 353: `todo: create separate method "handleOverpassErrorResponse(error)"`

#### Debugging Features
- **Console Logging**: Extensive feature-by-feature validation logs
- **Data Export**: Automatic download of raw GeoJSON data for analysis
- **Sample Logging**: Logs first few elements/features for inspection

#### Known Limitations
- **API Dependency**: Relies on external Overpass API availability
- **Static Export**: Always exports data to downloads folder
- **No Caching**: No persistent storage between sessions
- **Performance**: Large datasets may impact rendering performance

### Related Components

#### LocationCard.vue (`_components/location/`)
- **Purpose**: Display ATM location details with image and transaction types
- **Usage**: Could be integrated for location-based overlays on county map
- **Props**: `location` object with name, image, and transaction types

#### CoordinatesTable.vue  
- **Purpose**: Tabular display of coordinate arrays
- **Usage**: Could display county boundary coordinates or selected area points
- **Props**: `coordinates` array with lat/lng objects
- **Headers**: Estonian labels ("pikkus"/"laius" for longitude/latitude)

### Integration with Main Application

#### Routing
- **Route Name**: `mapCountyRoute`
- **Path**: `/map-county` 
- **Navigation**: Accessible via ExtraView.vue navigation buttons
- **Parent**: Part of the "Extras" section for demonstration features

#### Navigation Context
The component is accessed through the main extras navigation:
```javascript
// From ExtraView.vue
<button @click="$router.push({name: 'mapCountyRoute'})" 
        class="btn btn-outline-primary">
  Kaart maakond
</button>
```

### Future Development Opportunities

#### Suggested Improvements
1. **Method Extraction**: Implement TODO methods for better code organization
2. **Caching System**: Add localStorage or IndexedDB for offline capability  
3. **Performance**: Implement feature clustering for large datasets
4. **Integration**: Connect with ATM location data for overlay functionality
5. **Internationalization**: Support for multiple languages beyond Estonian/English
6. **Export Options**: Multiple export formats (KML, Shapefile, etc.)
7. **Real-time Updates**: Periodic data refresh with change detection