OpenATKLib
==========
[Classes And Functions](https://docs.google.com/document/d/1cxX2D9vl8VNLVELuPf5Uqk_jTwsIdi2En9od31LhaSw/edit?usp=sharing)

## **Setup**
### In your xml layout replace the google map fragment with:
\<fragment
         android:id="@+id/map"
        android:name="com.openatk.openatklib.atkmap.ATKSupportMapFragment"
         android:layout_width="match_parent"
         android:layout_height="match_parent" />

### In your FragmentActivity replace "SupportMapFragment" with "ATKSupportMapFragment":
`FragmentManager fm = getSupportFragmentManager();  `
`ATKSupportMapFragment fragmentMap = (ATKSupportMapFragment) fm.findFragmentById(R.id.map);`  

### Then call getAtkMap() in the place of getMap():
`ATKMap atkmap = fragmentMap.getAtkMap();`  

***

## **Usage**
### Drawing polygon:
`int polygonId = 0;`  
`ATKPolygonView polygonView = atkmap.drawPolygon(polygonId);`  

### Stop drawing:
`ATKPolygonView polygonView = atkmap.completePolygon();`  

### Adding existing polygon:
`List<LatLng> points = new ArrayList<LatLng>();`  
`points.add(new LatLng(40.46f,-86.96f));`  
`points.add(new LatLng(40.44f,-86.91f));`  
`points.add(new LatLng(40.43f,-86.90f));`  
`polygonId = 1;`  
`ATKPolygon polygon = new ATKPolygon(polygonId, points);`  
`ATKPolygonView added = atkmap.addPolygon(polygon);`  

### Editing polygon:
`added.getAtkPolygon().boundary.add(new LatLng(40.41f,-86.95f));`  
`atkmap.updatePolygon(added.getAtkPolygon());`  

### Click listener:
`added.setOnClickListener(new ATKPolygonClickListener(){`
				`@Override`
				`public boolean onClick(ATKPolygonView polygonView) {`
					`polygonView.setFillColor(1.0f, 255, 255, 0);`
					`return false;`
				`}`
			`});`


