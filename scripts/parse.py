
from elementtree import ElementTree as et

content = et.parse('SGpackages.xml')

def getBudget(days, places):
    total = days * 2 + places
    if total > 30.0:
        return "HIGH"
    elif total > 15.0:
        return "MEDIUM"
    else:
        return "LOW"

def getType(days):
    if days > 7.0:
        return "VERY LONG TOUR"
    elif days > 3.0:
        return "LONG TOUR"
    else:
        return "SHORT TOUR"


for c in content.findall('Package'):
        places = ''
        numberOfPlaces = 0
        for i in range(37):
            place = 'Place' + str(i)
            myplace = c.find(place)
            if myplace != None:
                places = places + myplace.text + " - "
                numberOfPlaces = numberOfPlaces + 1
        if ( type(c.find('references')) == type(None) ):
            refs = "No referecnces"
        else:
            refs = c.find('references').text

        try:
            days = float(c.find('Duration').text)
        except (ValueError, AttributeError):
            days = 0
            
        
            
        print c.find('id').text +  "\t" \
              + c.find('Name').text +   "\t" \
              + str(days)  +"\t" \
              + getType(days) + "\t" \
              + str(numberOfPlaces) + "\t" \
              + getBudget(days, numberOfPlaces) + "\t" \
              + places + "\t"  \
              + refs + "\t"  \
              #+ c.find('Description').text

    
