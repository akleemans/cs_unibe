package ch.eonum;

import java.util.HashMap;

/**
 * Resolves a city to geographical coordinates.
 * Provides the names and hard coded coordinates of the 120 largest Swiss cities.
 */
public class CityResolver
{
	private static final HashMap<String, City> cities = new HashMap<String, City>();
	private static final CityResolver instance = new CityResolver();

	/**
	 * Built as a Singleton, the list does not have to be created more than once.
	 * 
	 * @return An already existing instance if there has been created one before.
	 *         Otherwise a new instance is created and returned.
	 */
	public static CityResolver getInstance()
	{
		return instance;
	}

	private CityResolver()
	{
		cities.put("Aarau", new City("Aarau", 47.39254, 8.04422));
		cities.put("Adliswil", new City("Adliswil", 47.30997, 8.52462));
		cities.put("Aesch", new City("Aesch", 47.47104, 7.5973));
		cities.put("Affoltern am Albis", new City("Affoltern am Albis", 47.27743, 8.45128));
		cities.put("Allschwil", new City("Allschwil", 47.55074, 7.53599));
		cities.put("Altstätten", new City("Altstätten", 47.37766, 9.54746));
		cities.put("Amriswil", new City("Amriswil", 47.54699, 9.29836));
		cities.put("Arbon", new City("Arbon", 47.51667, 9.43333));
		cities.put("Arth", new City("Arth", 47.06337, 8.52348));
		cities.put("Baar", new City("Baar", 47.19625, 8.52954));
		cities.put("Baden", new City("Baden", 47.47333, 8.30592));
		cities.put("Basel", new City("Basel", 47.5584, 7.57327));
		cities.put("Bassersdorf", new City("Bassersdorf", 47.44342, 8.62851));
		cities.put("Bellinzona", new City("Bellinzona", 46.19278, 9.01703));
		cities.put("Belp", new City("Belp", 46.89129, 7.49825));
		cities.put("Bern", new City("Bern", 46.94809, 7.44744));
		cities.put("Binningen", new City("Binningen", 47.54021, 7.56932));
		cities.put("Birsfelden", new City("Birsfelden", 47.5529, 7.62322));
		cities.put("Brugg", new City("Brugg", 47.48096, 8.20869));
		cities.put("Buchs", new City("Buchs", 47.38789, 8.07375));
		cities.put("Bülach", new City("Bülach", 47.52197, 8.54049));
		cities.put("Bulle", new City("Bulle", 46.6195, 7.05674));
		cities.put("Burgdorf", new City("Burgdorf", 47.05901, 7.62786));
		cities.put("Carouge", new City("Carouge", 46.18096, 6.13921));
		cities.put("Cham", new City("Cham", 47.18213, 8.46358));
		cities.put("Chur", new City("Chur", 46.84986, 9.53287));
		cities.put("Davos", new City("Davos", 46.80429, 9.83723));
		cities.put("Dietikon", new City("Dietikon", 47.40165, 8.40015));
		cities.put("Dübendorf", new City("Dübendorf", 47.39724, 8.61872));
		cities.put("Ebikon", new City("Ebikon", 47.07937, 8.34041));
		cities.put("Ecublens", new City("Ecublens", 46.60735, 6.80895));
		cities.put("Einsiedeln", new City("Einsiedeln", 47.11667, 8.75));
		cities.put("Emmen", new City("Emmen", 47.0811, 8.30477));
		cities.put("Frauenfeld", new City("Frauenfeld", 47.55816, 8.89854));
		cities.put("Freienbach", new City("Freienbach", 47.20534, 8.75842));
		cities.put("Gland", new City("Gland", 46.42082, 6.2701));
		cities.put("Gossau", new City("Gossau", 47.41694, 9.25125));
		cities.put("Grenchen", new City("Grenchen", 47.1921, 7.39586));
		cities.put("Herisau", new City("Herisau", 47.38615, 9.27916));
		cities.put("Hinwil", new City("Hinwil", 47.29426, 8.84393));
		cities.put("Horgen", new City("Horgen", 47.25579, 8.60027));
		cities.put("Horw", new City("Horw", 47.01692, 8.30956));
		cities.put("Ittigen", new City("Ittigen", 46.97434, 7.48281));
		cities.put("Kloten", new City("Kloten", 47.45152, 8.58491));
		cities.put("Köniz", new City("Köniz", 46.92436, 7.41457));
		cities.put("Kreuzlingen", new City("Kreuzlingen", 47.65, 9.18333));
		cities.put("Kriens", new City("Kriens", 47.03537, 8.27631));
		cities.put("Küsnacht", new City("Küsnacht", 47.31805, 8.58401));
		cities.put("Küssnacht", new City("Küssnacht", 47.08557, 8.44206));
		cities.put("Lancy", new City("Lancy", 46.18969, 6.11578));
		cities.put("Langenthal", new City("Langenthal", 47.21526, 7.79607));
		cities.put("Lausanne", new City("Lausanne", 46.516, 6.63282));
		cities.put("Le Locle", new City("Le Locle", 47.05953, 6.75228));
		cities.put("Liestal", new City("Liestal", 47.48455, 7.73446));
		cities.put("Locarno", new City("Locarno", 46.17086, 8.79953));
		cities.put("Lugano", new City("Lugano", 46.01008, 8.96004));
		cities.put("Luzern", new City("Luzern", 47.05048, 8.30635));
		cities.put("Lyss", new City("Lyss", 47.0741, 7.30655));
		cities.put("Männedorf", new City("Männedorf", 47.25686, 8.69893));
		cities.put("Martigny", new City("Martigny", 46.08333, 7.08333));
		cities.put("Meilen", new City("Meilen", 47.27232, 8.64617));
		cities.put("Mendrisio", new City("Mendrisio", 45.86741, 8.9821));
		cities.put("Meyrin", new City("Meyrin", 46.23424, 6.08025));
		cities.put("Möhlin", new City("Möhlin", 47.55915, 7.84329));
		cities.put("Monthey", new City("Monthey", 46.25546, 6.96066));
		cities.put("Montreux", new City("Montreux", 46.43301, 6.91143));
		cities.put("Morges", new City("Morges", 46.51127, 6.49854));
		cities.put("Münchenstein", new City("Münchenstein", 47.51378, 7.62434));
		cities.put("Münsingen", new City("Münsingen", 46.87298, 7.561));
		cities.put("Muri bei Bern", new City("Muri bei Bern", 46.93238, 7.5001));
		cities.put("Muttenz", new City("Muttenz", 47.52271, 7.64511));
		cities.put("Neuhausen am Rheinfall", new City("Neuhausen am Rheinfall", 47.67924, 8.59938));
		cities.put("Nyon", new City("Nyon", 46.38318, 6.23955));
		cities.put("Oberwil", new City("Oberwil", 47.51407, 7.55786));
		cities.put("Oftringen", new City("Oftringen", 47.31382, 7.92533));
		cities.put("Olten", new City("Olten", 47.34999, 7.90329));
		cities.put("Onex", new City("Onex", 46.18391, 6.10181));
		cities.put("Opfikon", new City("Opfikon", 47.43169, 8.57588));
		cities.put("Ostermundigen", new City("Ostermundigen", 46.93456, 7.50435));
		cities.put("Pfäffikon", new City("Pfäffikon", 47.36453, 8.79202));
		cities.put("Pratteln", new City("Pratteln", 47.52071, 7.69356));
		cities.put("Prilly", new City("Prilly", 46.53938, 6.59266));
		cities.put("Pully", new City("Pully", 46.51027, 6.66183));
		cities.put("Regensdorf", new City("Regensdorf", 47.4341, 8.46874));
		cities.put("Reinach", new City("Reinach", 47.25944, 8.18845));
		cities.put("Renens", new City("Renens", 46.53989, 6.5881));
		cities.put("Rheinfelden", new City("Rheinfelden", 47.55437, 7.79403));
		cities.put("Richterswil", new City("Richterswil", 47.20622, 8.69686));
		cities.put("Riehen", new City("Riehen", 47.57884, 7.64683));
		cities.put("Rüti", new City("Rüti", 47.25603, 8.85552));
		cities.put("Schaffhausen", new City("Schaffhausen", 47.69732, 8.63493));
		cities.put("Schlieren", new City("Schlieren", 47.39668, 8.44763));
		cities.put("Schwyz", new City("Schwyz", 47.02786, 8.65611));
		cities.put("Solothurn", new City("Solothurn", 47.20791, 7.53714));
		cities.put("Spiez", new City("Spiez", 46.68473, 7.69111));
		cities.put("Spreitenbach", new City("Spreitenbach", 47.42016, 8.36301));
		cities.put("St. Gallen", new City("St. Gallen", 47.42214, 9.37554));
		cities.put("Stäfa", new City("Stäfa", 47.24254, 8.72342));
		cities.put("Steffisburg", new City("Steffisburg", 46.77807, 7.63249));
		cities.put("Thalwil", new City("Thalwil", 47.29175, 8.56351));
		cities.put("Thun", new City("Thun", 46.75118, 7.62166));
		cities.put("Uster", new City("Uster", 47.34713, 8.72091));
		cities.put("Uzwil", new City("Uzwil", 47.43813, 9.13922));
		cities.put("Vernier", new City("Vernier", 46.21702, 6.08497));
		cities.put("Versoix", new City("Versoix", 46.26667, 6.16667));
		cities.put("Vevey", new City("Vevey", 46.46116, 6.84328));
		cities.put("Volketswil", new City("Volketswil", 47.39259, 8.68949));
		cities.put("Wädenswil", new City("Wädenswil", 47.22683, 8.6687));
		cities.put("Wallisellen", new City("Wallisellen", 47.41499, 8.59672));
		cities.put("Weinfelden", new City("Weinfelden", 47.56667, 9.1));
		cities.put("Wettingen", new City("Wettingen", 47.4705, 8.31636));
		cities.put("Wetzikon", new City("Wetzikon", 47.53765, 9.00134));
		cities.put("Wil", new City("Wil", 47.60447, 8.50815));
		cities.put("Winterthur", new City("Winterthur", 47.5, 8.75));
		cities.put("Wohlen", new City("Wohlen", 47.35236, 8.27877));
		cities.put("Worb", new City("Worb", 46.92984, 7.56306));
		cities.put("Zofingen", new City("Zofingen", 47.28779, 7.94586));
		cities.put("Zollikon", new City("Zollikon", 47.34019, 8.57407));
		cities.put("Zug", new City("Zug", 47.17242, 8.51744));
		cities.put("Zürich", new City("Zürich", 47.36667, 8.55));
	}

	public City getCity(String type)
	{
		return cities.get(type);
	}

	public String[] getAllCities()
	{
		return cities.keySet().toArray(new String[] {});
	}
}
