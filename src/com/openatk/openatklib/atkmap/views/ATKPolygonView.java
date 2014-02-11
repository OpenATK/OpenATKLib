package com.openatk.openatklib.atkmap.views;


import java.util.ArrayList;
import java.util.List;

import pl.mg6.android.maps.extensions.GoogleMap;
import pl.mg6.android.maps.extensions.Polygon;
import pl.mg6.android.maps.extensions.PolygonOptions;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.openatk.openatklib.atkmap.listeners.ATKPolygonClickListener;
import com.openatk.openatklib.atkmap.models.ATKPolygon;

public class ATKPolygonView {
	private ATKPolygon polygon;
	private GoogleMap map;
	
	
	private Polygon mapPolygon;
	private PolygonOptions polygonOptions;
	private ATKPolygonClickListener clickListener;
	
	private int strokeColor = Color.argb(150, 150, 150, 150);
	private int fillColor = Color.argb(200, 200, 200, 200);
	private float strokeWidth = 3.0f;
	private boolean visible = true;
	private float zindex = 1.0f;
	
	public ATKPolygonView(GoogleMap map, ATKPolygon polygon){
		this.map = map;
		this.polygon = polygon;
		this.drawPolygon();
	}
	
	public void setOnClickListener(ATKPolygonClickListener clickListener){
		this.clickListener = clickListener;
	}
	
	public ATKPolygon getAtkPolygon(){
		return polygon;
	}
	
	public void setAtkPolygon(ATKPolygon polygon){
		this.polygon = polygon; //If the whole model changed
		this.drawPolygon();
	}
	
	public void update(){
		this.drawPolygon();
	}
	
	public void remove(){
		this.mapPolygon.remove();
		this.mapPolygon = null;
	}
	
	public void hide(){
		this.visible = false;
		if(this.mapPolygon != null){
			this.mapPolygon.setVisible(false);
		}
	}
	
	public void show(){
		this.visible = false;
		if(this.mapPolygon != null){
			this.mapPolygon.setVisible(true);
		}
	}
	
	public void setStrokeColor(int color){
		this.strokeColor = color;
		if(this.mapPolygon != null) this.mapPolygon.setStrokeColor(this.strokeColor);
	}
	
	public void setStrokeColor(float alpha, int red, int green, int blue){
		this.strokeColor = Color.argb((int)(alpha * 255),  red, green, blue);
		if(this.mapPolygon != null) this.mapPolygon.setStrokeColor(this.strokeColor);
	}
	
	public void setFillColor(int color){
		this.fillColor = color;
		if(this.mapPolygon != null) this.mapPolygon.setFillColor(this.fillColor);
	}
	
	public void setFillColor(float alpha, int red, int green, int blue){
		this.fillColor = Color.argb((int)(alpha * 255),  red, green, blue);
		if(this.mapPolygon != null) this.mapPolygon.setFillColor(this.fillColor);
	}
	
	public void setStrokeWidth(float width){
		this.strokeWidth = width;
		if(this.mapPolygon != null) this.mapPolygon.setStrokeWidth(this.strokeWidth);
	}

	public void setOpacity(float opacity){
		this.fillColor = Color.argb((int)(opacity * 255), Color.red(this.fillColor), Color.green(this.fillColor), Color.blue(this.fillColor));
		if(this.mapPolygon != null) this.mapPolygon.setFillColor(this.fillColor);
	}
	
	public void setZIndex(float zindex){
		this.zindex = zindex;
		if(this.mapPolygon != null) this.mapPolygon.setZIndex(this.zindex );
	}
	
	public float getZIndex(){
		return this.zindex;
	}
	
	public boolean wasClicked(Point point){ //TODO protected?
		//Returns true if clicked, false otherwise	
		//TODO Speed improvement, store bounding box.
		//Convert latlngs to points
		List<Point> pointsBoundary = new ArrayList<Point>();
		Projection proj = map.getProjection();
		for(int i=0; i<this.polygon.boundary.size(); i++){
			Point aPoint = proj.toScreenLocation(this.polygon.boundary.get(i));
			pointsBoundary.add(aPoint);
		}
		if(isPointInPolygon(point, pointsBoundary)) return true;
		return false;
	}
	
	public boolean wasClicked(LatLng point){ //TODO protected?
		//Returns null if wasn't clicked, true or false if clicked depending if we consumed it		
		Point position = this.map.getProjection().toScreenLocation(point);
		return this.wasClicked(position);
	}
	
	public boolean click(){ //TODO protected?
		//Returns true or false depending if listener consumed the click event		
		if(this.clickListener != null){
			return this.clickListener.onClick(this); //Return if we consumed the click
		}
		return false;
	}
		
	private void drawPolygon(){
		Log.d("atkPolygonView", "drawPolygon");
		if(this.polygon.boundary != null && this.polygon.boundary.size() > 0){
			if(this.mapPolygon == null){
				Log.d("atkPolygonView", "Creating polygon");
				//Setup options
				this.polygonOptions = new PolygonOptions();			
				this.polygonOptions.addAll(polygon.boundary);
				this.polygonOptions.strokeColor(this.strokeColor);
				this.polygonOptions.strokeWidth(this.strokeWidth);
				this.polygonOptions.fillColor(this.fillColor);
				this.polygonOptions.visible(this.visible);
				this.polygonOptions.zIndex(this.zindex);
				this.mapPolygon = map.addPolygon(this.polygonOptions);
			} else {
				Log.d("atkPolygonView", "Updating # points:" + Integer.toString(this.polygon.boundary.size()));
				this.mapPolygon.setPoints(this.polygon.boundary);
			}
		} else {
			Log.d("atkPolygonView", "removing");
			//Model doesn't have a boundary remove the polygon from the map
			if(this.mapPolygon != null) this.mapPolygon.remove();
			this.mapPolygon = null;
		}
	}

	private boolean isPointInPolygon(Point tap, List<Point> vertices) {
		int intersectCount = 0;
		for (int j = 0; j < vertices.size() - 1; j++) {
			if (rayCastIntersect(tap, vertices.get(j), vertices.get(j + 1))) {
				intersectCount++;
			}
		}
		return ((intersectCount % 2) == 1); // odd = inside, even = outside;
	}

	private boolean rayCastIntersect(Point tap, Point vertA, Point vertB) {
		double aY = vertA.y;
		double bY = vertB.y;
		double aX = vertA.x;
		double bX = vertB.x;
		double pY = tap.y;
		double pX = tap.x;

		if ((aY > pY && bY > pY) || (aY < pY && bY < pY)
				|| (aX < pX && bX < pX)) {
			return false; // a and b can't both be above or below pt.y, and a or b must be east of pt.x
		}
		
		//If both a and b are east of point tapped at this point then we are good to go
		if(aX > pX && bX > pX){
			return true;
		}

		double m = (aY - bY) / (aX - bX); // Rise over run
		double bee = (-aX) * m + aY; // y = mx + b
		double x = (pY - bee) / m; // algebra is neat!

		return x > pX;
	}
}
