package hu.nem3d.zincity.ATempClasses;

import hu.nem3d.zincity.Cell.IndustrialZoneCell;
import hu.nem3d.zincity.Cell.LivingZoneCell;
import hu.nem3d.zincity.Cell.RoadCell;
import hu.nem3d.zincity.Cell.ServiceZoneCell;

public class Main {
    /*public static void main(String[] args) {
        mapTest();
    }*/

    static void mapTest(){
        /*
		Generate this and search here
		[ ] [x] [r] [r] [r] [r]
		[h] [ ] [r] [s] [ ] [ ]
		[r] [r] [r] [ ] [ ] [ ]
		[ ] [ ] [r] [ ] [x] [ ]
		[ ] [ ] [r] [r] [r] [r]
		[ ] [ ] [r] [ ] [s] [ ]
		 */
        TestMap m = new TestMap(6, 6);
        int cap = 1;
        LivingZoneCell myhome = new LivingZoneCell(1, 0, cap);
        m.addCell(1, 0, myhome);
        m.addCell(0, 2, new RoadCell(0, 2));
        m.addCell(1, 2, new RoadCell(1, 2));
        m.addCell(2, 2, new RoadCell(2, 2));
        m.addCell(3, 2, new RoadCell(3, 2));
        m.addCell(4, 2, new RoadCell(4, 2));
        m.addCell(5, 2, new RoadCell(0, 2));
        m.addCell(0, 3, new RoadCell(0, 3));
        m.addCell(0, 4, new RoadCell(0, 4));
        m.addCell(0, 5, new RoadCell(0, 5));
        m.addCell(2, 0, new RoadCell(2, 0));
        m.addCell(2, 1, new RoadCell(2, 1));
        m.addCell(4, 3, new RoadCell(4, 3));
        m.addCell(4, 4, new RoadCell(4, 4));
        m.addCell(4, 5, new RoadCell(4, 5));
        m.addCell(1, 5, new IndustrialZoneCell(1, 5, cap));
        m.addCell(3, 4, new IndustrialZoneCell(3, 4, cap));
        m.addCell(1, 3, new ServiceZoneCell(1, 3, cap));
        m.addCell(5, 4, new ServiceZoneCell(5, 4, cap));
        m.fillRest();
        System.out.println(m.toString() + "\n");
        IndustrialZoneCell clInd = m.closestIndustrial(myhome);
        System.out.println("Closest Industrial: " + clInd.getX() + " " + clInd.getY());
		/*Service clServ = m.closestService(myhome);
		System.out.println("Closest Service: " + clServ.x + " " + clServ.y);*/
    }
}


