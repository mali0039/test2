use 5.010001;
nonHash();
//Because we are utilizing key-values (storing a person's name and age), a hash is more
efficient because that is its exact purpose. We use the name as the key which will allow
us to directly access the value. In my example I utilized two arrays which takes more space
but is also slower in finding the values we are looking for (specifically in the case of handling
duplicate values/collisons.) The hash table is more readable due to the ease of understanding
how the values are being stored (name -> age). Also the use of two arrays complicates the nonhash
method a bit more. The hash is also easier to program given understanding of how hashes work.//
sub nonHash() {
    my @letters=(a..z);
    my $total=@letters;

    my @names;
    my @ages;
    $randomName = "";

    for ($a = 0; $a < 100; $a = $a + 1) {
        for ($b = 0; $b < 3; $b = $b + 1) {
            $newletter = $letters[rand $total];
            $randomName = $randomName . $newletter;
        }
        if ( $randomName ~~ @names ) {
            print "$randomName already exists!";
        }
        else {
            $x = 18 + int(rand(60 - 18));
            $names[$a] = $randomName;
            $ages[$a] = $x;
        }
        $randomName = "";
    }

    for ($c = 0; $c < scalar @names; $c = $c + 1) {
        print "Name: $names[$c], Age: $ages[$c]\n";
    }
}
sub usingHash() {
    my @letters=(a..z);
    my $total=@letters;

    my %people;
    $randomName = "";

    for ($a = 0; $a < 100; $a = $a + 1) {
        for ($b = 0; $b < 3; $b = $b + 1) {
            $newletter = $letters[rand $total];
            $randomName = $randomName . $newletter;
        }
        if(exists($people{$randomName})) {
            print "The person: $randomName already exists!\n";
        }
        else {
            $x = 18 + int(rand(60 - 18));
            $people{$randomName} = $x;
        }
        $randomName = "";
    }

    foreach my $key (keys %people) {
        $value = $people{$key};
        print "Value of $key is $value\n"; 
    }

}
