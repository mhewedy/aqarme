# aqarme

Service to query https://sa.aqar.fm/ for certain criteria and notifies me back by Facebook messenger on the list of aprtements 
that matches my creiteria.


The list of criteria now includes, price, geo location, and has Images.

The main action is here:

```java
aprtList.stream()
	.filter(this::notProcessed)         // skipped already processed items
	.peek(it -> sleep())                // sleep 5 seconds so not to be blocked by aqar.fm
	.filter(this::matchesPrice)         // filter by price
	.filter(this::hasImage)             // filter by having image
	.map(this::detailsPage)             // get the details page of the advertise
	.filter(this::insideSelectedArea)   // check the lat&long to be inside the selected area on map
	.filter(this::hasElevator)          // check to has elavator
	.filter(this::hasMoreThanOneRoom)   // check to has more than 1 room
```

And then the matched result is sent to me via facebook messagener using [Send API](https://developers.facebook.com/docs/messenger-platform/send-api-reference).

The `insideSelectedArea()` checks the coordinates according to the selected area on maps like the following one:
<p align="center">
<img src="https://github.com/mhewedy/aqarme/raw/master/src/main/resources/polygon.png" width="500">
</p>


Add job using

```bash
curl -H "Content-Type: application/json" -d '{"clientId": "client-id-1", "name": "my first job", "senders": "966593642012,00201095771359", "jobDetail": {"vertexes": "24.675722;46.556282,24.669794;46.542549,24.617369;46.568298,24.60426;46.588898,24.621115;46.656532,24.63984;46.647949,24.645145;46.625633,24.677906;46.640396,24.710034;46.618767,24.735606;46.590614", "maxPrice": "25000", "hasImages": "true", "hasElevator": "true", "numRooms": "2,3", "floorNumber": "1,2"}}' http://localhost:8080/api/job
```