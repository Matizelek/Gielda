package time.exchangeDate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ExchengeDateRepository {

    private final List<ExchangeDatePair> exchangeDatePairs;
    private int dateRange = 0;
    private int size = 0;
    private ExchangeDate firstDay;
    private ExchangeDate lastDay;
    private int gapsCount;


    public ExchengeDateRepository(List<ExchangeDate> exchangeDates) {
        this.exchangeDatePairs = sortAndSetNextDate(exchangeDates);
        setVariables();
    }

    private void setVariables() {
        size = exchangeDatePairs.size();
        if (size < 1) {
            throw new IllegalStateException();
        }
        firstDay = exchangeDatePairs.get(0).getDate();
        lastDay = exchangeDatePairs.get(size - 1).getDate();
        dateRange = (int) TimeUnit.DAYS.convert(lastDay.getDate().getTime() - firstDay.getDate().getTime(),
                TimeUnit.MILLISECONDS);
        for (ExchangeDatePair ed : exchangeDatePairs) {
            if (ed.getDayDifferenceBetweenDates() > 1) {
                gapsCount += ed.getDayDifferenceBetweenDates() - 1;
            }

        }
    }

    public int getDateRepositoryRange() {
        return dateRange;
    }

    public int getDatesCount() {
        return size;
    }

    public String getDetails() {
        String datails = "Zawartosc repository:\n"
                + "Data Poczatkowa: " + exchangeDatePairs.get(0) + "\n"
                + "Data Koncowa: " + exchangeDatePairs.get(size - 1) + "\n"
                + "Zakres dat: " + getDateRepositoryRange() + " dni\n"
                + "Ilosc dat w repository: " + getDatesCount() + "\n";
        if (getGapsCount() > 0) {
            datails += "Przerwy w datach: \n"
                    // + getGapsString()
                    + "Laczna ilosc przerw: " + getGapsCount() + " dni";
        } else {
            datails += "Brak przerw";
        }

        return datails;
    }

    public int getGapsCount() {
        return gapsCount;
    }


    /* public String getGapsString() {
         return exchangeDatePairs.stream().map(e -> e.getDifferenceBetweenDates() > 1 ?
                 "\tMiedzy " + e.toString() + " a " + e.getNextExchengeDateString() + " przerwa " + e.getDifferenceBetweenDates() + " dni\n"
                 : "").collect(Collectors.joining(""));
     }
 */
    public String getList() {
        return exchangeDatePairs.stream().map(e -> e.toString()).collect(Collectors.joining("\n"));
    }


    private List<ExchangeDatePair> sortAndSetNextDate(List<ExchangeDate> exchangeDates) {
        List<ExchangeDate> exchangeDates2 = exchangeDates
                .stream()
                .distinct()
                .sorted(Comparator.comparing(o -> o.getDate()))
                .collect(Collectors.toList());

        List<ExchangeDatePair> exchangePairs = exchangeDates2
                .stream()
                .map(exchangeDate -> {

                            int indexOf = exchangeDates2.indexOf(exchangeDate);
                            //TODO refactor

                            if (indexOf != -1) {
                                Optional<ExchangeDate> optionalExchangeDate = getOptional(exchangeDates2, indexOf + 1);
                                if (optionalExchangeDate.isPresent())
                                    return new ExchangeDatePair(exchangeDate, exchangeDates2.get(indexOf + 1));
                                else
                                    return new ExchangeDatePair(exchangeDate, null);
                            } else {
                                return new ExchangeDatePair(exchangeDate, null);
                            }

                        }

                ).collect(Collectors.toList());


        return exchangePairs;
    }

    public ExchangeDate getFirstDay() {
        return firstDay;
    }

    public ExchangeDate getLastDay() {
        return lastDay;
    }

    public List<ExchangeDatePair> getExchangeDatePairs() {
        return exchangeDatePairs;
    }

    private <T> Optional<T> getOptional(List<T> list, int index) {
        try {
            return Optional.of(list.get(index));
        } catch (IndexOutOfBoundsException e) {
            return Optional.empty();
        }

    }
}
