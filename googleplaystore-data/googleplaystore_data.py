import pandas as pd

def read_apps(filename):
    def remove_duplicate_rows(dataframe):
        """Apps are repeated for no obvious reason.
        The review count looks cumulative, so we keep only the records with
        the highest review count per app.
        """
        indexes_to_keep =\
            dataframe.groupby(by=APP_COL, as_index=False)[REVIEWS_COL].idxmax()
        unique_apps = dataframe.reindex(indexes_to_keep, copy=True)
        return unique_apps

    read_data = pd.read_csv(filename)
    unique_rows = remove_duplicate_rows(read_data)
    del read_data
    return unique_rows

def get_apps_by_review_count(dataframe):
    """
    For each review count, collects the apps which belong to that review count.
    :param dataframe: a dataset which contains an App and a Reviews column
    :return: key-value pairs of (review_count: list_of_apps) in ascending order
    """
    groups_by_review_count = dataframe.groupby(by=REVIEWS_COL, as_index=True)
    apps_by_review_count = groups_by_review_count[APP_COL].apply(list)
    return apps_by_review_count

def get_top_from_series(series, top_n=1):
    """
    Orders the series by ascending index.
    :return: dict with the top_n indexes and their associated values
    """
    return series.sort_index(ascending=True).head(top_n)

def print_series_keys_and_values(series):
    for key, value in series.iteritems():
        print(key)
        print('=========')
        try:
            iter(value)
            [print(item) for item in sorted(value, key=str.lower)]
        except TypeError as te:
            print(value)
        print()
    return


########### Exercises ###############

APP_COL = 'App'
REVIEWS_COL = 'Reviews'

# Manually edited source CSV as it was malformed:
# App 'Life Made WI-Fi Touchscreen Photo Frame' didn't have a Category col.
APPS = read_apps("googleplaystore.csv")

pd.set_option('display.width', 1000)
pd.set_option('display.max_columns', 15)


print("*** Exercise 1 ***")
print("Which app has the lowest number of reviews?")
print("Listed by review count:\n")

review_counts = get_apps_by_review_count(APPS)
least_reviews = get_top_from_series(review_counts)
print_series_keys_and_values(least_reviews)

# Cannot continue, got reassigned to another project